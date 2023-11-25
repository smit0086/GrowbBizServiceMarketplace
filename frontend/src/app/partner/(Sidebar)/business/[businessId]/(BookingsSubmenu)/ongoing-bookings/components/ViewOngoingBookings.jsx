"use client";

import * as React from "react";
import OngoingBookingCard from "./OngoingBookingCard";

const ViewOngoingBookings = ({ authSession, ongoingBookings }) => {
    return (
        <div>
            {ongoingBookings.length === 0 ?
                <div className="p-8 pl-16">
                    No Ongoing Bookings.
                </div>
                :
                <div>
                    <div className="flex flex-wrap gap-4" style={{marginLeft: '3%'}}>
                        {ongoingBookings.map((ongoingBooking) => (
                            <OngoingBookingCard key={ongoingBooking.id} authSession={authSession} ongoingBooking={ongoingBooking} />
                        ))}
                    </div>
                </div>
            }
        </div>
    )
}

export default ViewOngoingBookings;
