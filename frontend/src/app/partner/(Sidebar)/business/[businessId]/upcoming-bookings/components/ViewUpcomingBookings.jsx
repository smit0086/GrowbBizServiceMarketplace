"use client";

import * as React from "react";
import UpcomingBookingCard from "./UpcomingBookingCard";

const ViewUpcomingBookings = ({ authSession, upcomingBookings }) => {
    return (
        <div>
            {upcomingBookings.length === 0 ?
                <div className="p-8 pl-16">
                    No Upcoming Bookings.
                </div>
                :
                <div>
                    <div className="flex flex-wrap gap-4" style={{ marginLeft: '3%' }}>
                        {upcomingBookings.map((upcomingBooking) => (
                            <UpcomingBookingCard authSession={authSession} upcomingBooking={upcomingBooking} />
                        ))}
                    </div>
                </div>
            }
        </div>
    )
}

export default ViewUpcomingBookings;