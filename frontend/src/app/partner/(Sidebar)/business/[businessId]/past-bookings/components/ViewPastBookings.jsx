"use client";

import * as React from "react";
import PastBookingCard from "./PastBookingCard";

const ViewPastBookings = ({pastBookings }) => {
    return (
        <div>
            {pastBookings.length === 0 ?
                <div className="p-8 pl-16">
                    No Past Bookings.
                </div>
                :
                <div>
                    <div className="flex flex-wrap gap-4" style={{marginLeft: '3%'}}>
                        {pastBookings.map((pastBooking) => (
                            <PastBookingCard key={pastBooking.id} pastBooking={pastBooking} />
                        ))}
                    </div>
                </div>
            }
        </div>
    )
}

export default ViewPastBookings;