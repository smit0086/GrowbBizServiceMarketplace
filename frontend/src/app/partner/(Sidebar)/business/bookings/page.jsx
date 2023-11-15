import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import ViewUpcomingBookings from "./components/ViewUpcomingBookings";
import ViewOngoingBookings from "./components/ViewOngoingBookings";
import { ScrollArea } from "@/components/ui/scroll-area";

export default async function UpcomingBookingsManagement() {
    const authSession = await getServerSession(authOptions);
    const ongoingBookings = [
        {
            bookingId: '1',
            serviceName: "Haircut",
            timeRequired: 60,
            bookingDate: "2023-11-15",
            bookingStartTime: "10:00 AM",
            bookingEndTime: "11:00 AM",
            customerEmail: "john.doe@example.com",
            note: "Regular haircut"
        }
    ];
    const upcomingBookings = [
        {
            bookingId: '2',
            serviceName: "Massage",
            timeRequired: 90,
            bookingDate: "2023-11-16",
            bookingStartTime: "2:30 PM",
            bookingEndTime: "4:00 PM",
            customerEmail: "jane.smith@example.com",
            note: "Deep tissue massage"
        }
    ];

    return (
        <>
            <ScrollArea className="h-[100vh] w-[95vw] rounded-md p-4">
                <div>
                    <h2 className="text-3xl p-8 pl-16">Ongoing Bookings</h2>
                    <ViewOngoingBookings authSession={authSession} ongoingBookings={ongoingBookings} />
                    <h2 className="text-3xl p-8 pl-16">Upcoming Bookings</h2>
                    <ViewUpcomingBookings authSession={authSession} upcomingBookings={upcomingBookings} />
                </div>
            </ScrollArea>
        </>
    );
}