"use client";

import * as React from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";

const ViewUpcomingBookings = ({upcomingBookings }) => {
    return (
        <div>
            {upcomingBookings.length === 0 ?
                <div className="p-8 pl-16">
                    No Upcoming Bookings.
                </div>
                :
                <div>
                    <div className="flex flex-wrap gap-4" style={{marginLeft: '3%'}}>
                        {upcomingBookings.map((upcomingBooking) => (
                            <Card key={upcomingBooking.bookingId} className="w-[350px] ml-5">
                                <CardHeader>
                                    <CardTitle>{upcomingBooking.serviceName}</CardTitle>
                                    <CardDescription>Booking Details</CardDescription>
                                </CardHeader>
                                <CardContent>
                                    <div className="grid w-full items-center gap-4">
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Time Required</Label>
                                            <span id={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.timeRequired} minutes</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Date</Label>
                                            <span id={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.bookingDate}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Start Time</Label>
                                            <span id={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.bookingStartTime}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>End Time</Label>
                                            <span id={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.bookingEndTime}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Customer Email</Label>
                                            <span id={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.customerEmail}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Note</Label>
                                            <span id={`service-${upcomingBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.note}</span>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                </div>
            }
        </div>
    )
}

export default ViewUpcomingBookings;