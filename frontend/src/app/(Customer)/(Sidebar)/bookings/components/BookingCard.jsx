import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import React from "react";

const BookingCard = (props) => {
    const booking = props.booking;
    return (
        <Card className="w-[300px] my-4 mr-4">
            <CardContent className="pt-6">
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col">
                        <Label style={{ fontSize: "1rem", fontWeight: "bold" }}>
                            Service
                        </Label>
                        <span style={{ fontSize: "0.875rem" }}>
                            <a
                                className="hover:underline"
                                href={`/service/${booking.service.serviceId}/booking`}
                            >
                                {booking.service.serviceName}
                            </a>
                        </span>
                    </div>
                    <div className="flex flex-col">
                        <Label style={{ fontSize: "1rem", fontWeight: "bold" }}>
                            Date
                        </Label>
                        <span style={{ fontSize: "0.875rem" }}>
                            {booking.date}
                        </span>
                    </div>
                    <div className="flex flex-col">
                        <Label style={{ fontSize: "1rem", fontWeight: "bold" }}>
                            Start time
                        </Label>
                        <span style={{ fontSize: "0.875rem" }}>
                            {booking.startTime[0]}:{booking.startTime[1]}
                        </span>
                    </div>
                    <div className="flex flex-col">
                        <Label style={{ fontSize: "1rem", fontWeight: "bold" }}>
                            End time
                        </Label>
                        <span style={{ fontSize: "0.875rem" }}>
                            {booking.endTime[0]}:{booking.endTime[1]}
                        </span>
                    </div>
                    <div className="flex flex-col">
                        <Label style={{ fontSize: "1rem", fontWeight: "bold" }}>
                            Amount
                        </Label>
                        <span style={{ fontSize: "0.875rem" }}>
                            {booking.amount / 100} CAD
                        </span>
                    </div>
                    <div className="flex flex-col">
                        <Label
                            style={{
                                fontSize: "1rem",
                                fontWeight: "bold",
                            }}
                        >
                            Note
                        </Label>
                        <span style={{ fontSize: "0.875rem" }}>
                            {booking.note ?? "N/A"}
                        </span>
                    </div>
                </div>
            </CardContent>
        </Card>
    );
};

export default BookingCard;
