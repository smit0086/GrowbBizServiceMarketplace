import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import ViewPastBookings from "./components/ViewPastBookings";
import { ScrollArea } from "@/components/ui/scroll-area";
import { getPastBookingsForBusiness } from "@/services/bookingService";

export default async function PastBookingsManagement(context) {
    const authSession = await getServerSession(authOptions);
    const businessId = Number(context.params.businessId);

    let pastBookings = await getPastBookingsForBusiness(authSession.apiToken, businessId);
    pastBookings = pastBookings.map(pastBooking => {
        return {
            ...pastBooking,
            startTime: convertTimeToAMPMFormat(pastBooking.startTime),
            endTime: convertTimeToAMPMFormat(pastBooking.endTime),
            timeRequired: convertToMinutes(pastBooking.timeRequired)
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
                    <h2 className="text-3xl p-8 pl-16">Past Bookings</h2>
                    <ViewPastBookings authSession={authSession} pastBookings={pastBookings} />
                </div>
            </ScrollArea>
        </>
    );
}