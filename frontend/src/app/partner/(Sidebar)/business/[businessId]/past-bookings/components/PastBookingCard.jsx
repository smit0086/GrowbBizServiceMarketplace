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

const PastBookingCard = ({ pastBooking }) => {
    return (
        <Card key={pastBooking.bookingId} className="w-[350px] ml-5">
            <CardHeader>
                <CardTitle>{pastBooking.serviceName}</CardTitle>
                <CardDescription>Booking Details</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${pastBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Time Required</Label>
                        <span id={`service-${pastBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{pastBooking.timeRequired} minutes</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${pastBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Date</Label>
                        <span id={`service-${pastBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{pastBooking.date}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${pastBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Start Time</Label>
                        <span id={`service-${pastBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{pastBooking.startTime}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${pastBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>End Time</Label>
                        <span id={`service-${pastBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{pastBooking.endTime}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${pastBooking.bookingId}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Customer Email</Label>
                        <span id={`service-${pastBooking.bookingId}`} style={{ fontSize: '0.875rem' }}>{pastBooking.userEmail}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${pastBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Note</Label>
                        {(pastBooking.note === null || pastBooking.note === "") ?
                            <span id={`service-${pastBooking.id}`} style={{ fontSize: '0.875rem' }}>Customer does not require extra attention.</span>
                            :
                            <span id={`service-${pastBooking.id}`} style={{ fontSize: '0.875rem' }}>{pastBooking.note}</span>
                        }
                    </div>
                </div>
            </CardContent>
        </Card>
    )
}

export default PastBookingCard;