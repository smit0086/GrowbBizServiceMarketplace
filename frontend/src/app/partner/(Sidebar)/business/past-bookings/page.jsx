import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import ViewPastBookings from "./components/ViewPastBookings";
import { ScrollArea } from "@/components/ui/scroll-area";

export default async function PastBookingsManagement() {
    const authSession = await getServerSession(authOptions);
    const pastBookings = [
        {
            bookingId: '1',
            serviceName: "Haircut",
            timeRequired: 60,
            bookingDate: "2023-11-15",
            bookingStartTime: "10:00 AM",
            bookingEndTime: "11:00 AM",
            customerEmail: "john.doe@example.com",
            note: "Regular haircut",
            status: "Completed"
        },
        {
            bookingId: '2',
            serviceName: "Massage",
            timeRequired: 90,
            bookingDate: "2023-11-16",
            bookingStartTime: "2:30 PM",
            bookingEndTime: "4:00 PM",
            customerEmail: "jane.smith@example.com",
            note: "Deep tissue massage",
            status: "Completed"
        }
    ];

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