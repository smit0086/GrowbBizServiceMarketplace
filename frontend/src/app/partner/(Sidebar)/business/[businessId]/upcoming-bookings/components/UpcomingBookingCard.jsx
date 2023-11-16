"use client";

import * as React from "react";
import { useState } from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import { useForm } from "react-hook-form";
import { Icons } from "@/components/icons";
import { updateBookingStatus } from "@/services/bookingService";
import { BOOKING_STATUS } from "@/lib/constants";

const UpcomingBookingCard = ({ authSession, upcomingBooking }) => {
    const [isOngoingConfirmationDialoagOpen, setOngoingConfirmationDialoagOpen] = useState(false);
    const { handleSubmit, control, formState: { errors } } = useForm();
    const [isLoading, setIsLoading] = useState(false);

    const handleOngoingStatus = async (data) => {
        setIsLoading(true);
        await updateBookingStatus(authSession.apiToken, data.upcomingBooking.id, BOOKING_STATUS.ONGOING);
        setIsLoading(false);
        setOngoingConfirmationDialoagOpen(false);
        window.location.reload();
    }

    return (
        <Card key={upcomingBooking.id} className="w-[350px] ml-5">
            <CardHeader>
                <CardTitle>{upcomingBooking.serviceName}</CardTitle>
                <CardDescription>Booking Details</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${upcomingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Time Required</Label>
                        <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.timeRequired} minutes</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${upcomingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Date</Label>
                        <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.date}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${upcomingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Start Time</Label>
                        <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.startTime}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${upcomingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>End Time</Label>
                        <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.endTime}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${upcomingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Customer Email</Label>
                        <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.userEmail}</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${upcomingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Amount</Label>
                        <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.amount} CAD</span>
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor={`service-${upcomingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Note</Label>
                        {(upcomingBooking.note === null || upcomingBooking.note === "") ?
                            <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>Customer does not require extra attention.</span>
                            :
                            <span id={`service-${upcomingBooking.id}`} style={{ fontSize: '0.875rem' }}>{upcomingBooking.note}</span>
                        }
                    </div>
                </div>
            </CardContent>
            <CardFooter>
                <div className="flex items-center space-x-4">
                    <Dialog
                        open={isOngoingConfirmationDialoagOpen}
                        onOpenChange={setOngoingConfirmationDialoagOpen}
                    >
                        <DialogTrigger asChild>
                            <Button>Set Ongoing</Button>
                        </DialogTrigger>
                        <DialogContent className="sm:max-w-[425px]">
                            <DialogHeader>
                                <DialogTitle>Confirmation</DialogTitle>
                            </DialogHeader>
                            <form
                                onSubmit={handleSubmit((formData) =>
                                    handleOngoingStatus({
                                        upcomingBooking,
                                        formData
                                    })
                                )}
                            >
                                <div style={{ marginBottom: "1.2rem" }}>
                                    <div>
                                        <Label
                                            htmlFor="name"
                                            className="text-right"
                                        >
                                            Are you sure you want to change the status to Ongoing?
                                        </Label>
                                    </div>
                                </div>
                                <DialogFooter>
                                    <Button
                                        type="submit"
                                        disabled={isLoading}
                                    >
                                        {isLoading && (
                                            <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                        )}
                                        Confirm
                                    </Button>
                                </DialogFooter>
                            </form>
                        </DialogContent>
                    </Dialog>
                </div>
            </CardFooter>
        </Card>
    )
}

export default UpcomingBookingCard;