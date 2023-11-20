import React from "react";
import { getServerSession } from "next-auth";
import { AlertCircle } from "lucide-react";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { BOOKING_STATUS } from "@/lib/constants";
import { getAllUserBookings } from "@/services/bookingService";
import BookingCard from "./components/BookingCard";

const NO_ONGOING_BOOKING_HEADER = "No ongoing bookings";
const NO_ONGOING_BOOKING_DESCRIPTION =
    "You don't have any ongoing bookings at the moment.";

const NO_UPCOMING_BOOKING_HEADER = "No upcoming bookings";
const NO_UPCOMING_BOOKING_DESCRIPTION =
    "You don't have any upcoming bookings at the moment.";

const NO_PAST_BOOKING_HEADER = "No past bookings";
const NO_PAST_BOOKING_DESCRIPTION = "We don't have any history on you.";

const BookingsPage = async () => {
    const session = await getServerSession(authOptions);
    const bookings = await getAllUserBookings(
        session.apiToken,
        session.user.email
    );
    const ongoingBookings = bookings.filter(
        (booking) => booking.status === BOOKING_STATUS.ONGOING
    );
    const upcomingBookings = bookings.filter(
        (booking) => booking.status === BOOKING_STATUS.UPCOMING
    );
    const pastBookings = bookings.filter(
        (booking) => booking.status === BOOKING_STATUS.COMPLETED
    );
    return (
        <div className="p-8 pl-16">
            <div className="mb-4">
                <h2 className="text-3xl">Ongoing bookings</h2>
                {ongoingBookings.length === 0 && (
                    <div className="py-8">
                        <Alert className="max-w-[500px]">
                            <AlertCircle className="h-4 w-4" />
                            <AlertTitle>{NO_ONGOING_BOOKING_HEADER}</AlertTitle>
                            <AlertDescription>
                                {NO_ONGOING_BOOKING_DESCRIPTION}
                            </AlertDescription>
                        </Alert>
                    </div>
                )}
                <div className="flex flex-wrap">
                    {ongoingBookings.map((booking) => (
                        <BookingCard booking={booking} key={booking.id} />
                    ))}
                </div>
            </div>
            <div className="mb-4">
                <h2 className="text-3xl">Upcoming bookings</h2>
                {upcomingBookings.length === 0 && (
                    <div className="py-8">
                        <Alert className="max-w-[500px]">
                            <AlertCircle className="h-4 w-4" />
                            <AlertTitle>
                                {NO_UPCOMING_BOOKING_HEADER}
                            </AlertTitle>
                            <AlertDescription>
                                {NO_UPCOMING_BOOKING_DESCRIPTION}
                            </AlertDescription>
                        </Alert>
                    </div>
                )}
                <div className="flex flex-wrap">
                    {upcomingBookings.map((booking) => (
                        <BookingCard booking={booking} key={booking.id} />
                    ))}
                </div>
            </div>
            <div className="mb-4">
                <h2 className="text-3xl">Past bookings</h2>
                {pastBookings.length === 0 && (
                    <div className="py-8">
                        <Alert className="max-w-[500px]">
                            <AlertCircle className="h-4 w-4" />
                            <AlertTitle>{NO_PAST_BOOKING_HEADER}</AlertTitle>
                            <AlertDescription>
                                {NO_PAST_BOOKING_DESCRIPTION}
                            </AlertDescription>
                        </Alert>
                    </div>
                )}
                <div className="flex flex-wrap">
                    {pastBookings.map((booking) => (
                        <BookingCard booking={booking} key={booking.id} />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default BookingsPage;
