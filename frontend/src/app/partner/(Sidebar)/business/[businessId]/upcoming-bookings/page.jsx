import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import ViewUpcomingBookings from "./components/ViewUpcomingBookings";
import { ScrollArea } from "@/components/ui/scroll-area";
import { getUpcomingBookingsForBusiness } from "@/services/bookingService";

export default async function UpcomingBookingsManagement(context) {
    const authSession = await getServerSession(authOptions);
    const businessId = Number(context.params.businessId);

    let upcomingBookings = await getUpcomingBookingsForBusiness(authSession.apiToken, businessId);
    upcomingBookings = upcomingBookings.map(upcomingBooking => {
        return {
            ...upcomingBooking,
            startTime: convertTimeToAMPMFormat(upcomingBooking.startTime),
            endTime: convertTimeToAMPMFormat(upcomingBooking.endTime),
            timeRequired: convertToMinutes(upcomingBooking.timeRequired)
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
                    <h2 className="text-3xl p-8 pl-16">Upcoming Bookings</h2>
                    <ViewUpcomingBookings authSession={authSession} upcomingBookings={upcomingBookings} />
                </div>
            </ScrollArea>
        </>
    );
}