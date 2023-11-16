import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import ViewOngoingBookings from "../ongoing-bookings/components/ViewOngoingBookings";
import { ScrollArea } from "@/components/ui/scroll-area";
import { getOngoingBookingsForBusiness } from "@/services/bookingService";

export default async function OngoingBookingsManagement(context) {
    const authSession = await getServerSession(authOptions);
    const businessId = Number(context.params.businessId);

    let ongoingBookings = await getOngoingBookingsForBusiness(authSession.apiToken, businessId);
    ongoingBookings = ongoingBookings.map(ongoingBooking => {
        return {
            ...ongoingBooking,
            startTime: convertTimeToAMPMFormat(ongoingBooking.startTime),
            endTime: convertTimeToAMPMFormat(ongoingBooking.endTime),
            timeRequired: convertToMinutes(ongoingBooking.timeRequired)
        }
    });

    function convertTimeToAMPMFormat(timeArray) {
        let [hour, minute] = timeArray;
        
        let period = 'AM';
        if (hour >= 12) {
            period = 'PM';
            if (hour > 12) {
                hour -= 12;
            }
        }
    
        return `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')} ${period}`;
    }

    function convertToMinutes(timeArray) {
        return timeArray[0] * 60 + timeArray[1];
    }

    return (
        <>
            <ScrollArea className="h-[100vh] w-[95vw] rounded-md p-4">
                <div>
                    <h2 className="text-3xl p-8 pl-16">Ongoing Bookings</h2>
                    <ViewOngoingBookings authSession={authSession} ongoingBookings={ongoingBookings} />
                </div>
            </ScrollArea>
        </>
    );
}