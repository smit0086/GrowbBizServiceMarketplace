import ViewUpcomingBookings from "./components/ViewUpcomingBookings";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

export default async function UpcomingBookingsManagement() {
    const authSession = await getServerSession(authOptions);
    const upcomingBookings = [
        {
            bookingId: '1',
            serviceName: "Haircut",
            timeRequired: 60,
            bookingDate: "2023-11-15",
            bookingStartTime: "10:00 AM",
            bookingEndTime: "11:00 AM",
            customerEmail: "john.doe@example.com",
            note: "Regular haircut"
        },
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
            <div>
                <h2 className="text-3xl p-8 pl-16">Upcoming Bookings</h2>
                <ViewUpcomingBookings authSession={authSession} upcomingBookings={upcomingBookings} />
            </div>
        </>
    );
}