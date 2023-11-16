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

const ViewOngoingBookings = ({ongoingBookings }) => {
    const [isCompletedConfirmationDialogOpen, setCompletedConfirmationDialogOpen] = useState(false);
    const { handleSubmit, control, formState: { errors } } = useForm();
    const [isLoading, setIsLoading] = useState(false);

    const handleCompletedStatus = (data) => {
        setIsLoading(true);
        setIsLoading(false);
        setCompletedConfirmationDialogOpen(false);
    }

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
                            <Card key={ongoingBooking.bookingId} className="w-[350px] ml-5">
                                <CardHeader>
                                    <CardTitle>{ongoingBooking.serviceName}</CardTitle>
                                    <CardDescription>Booking Details</CardDescription>
                                </CardHeader>
                                <CardContent>
                                    <div className="grid w-full items-center gap-4">
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${ongoingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Time Required</Label>
                                            <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>{ongoingBooking.timeRequired} minutes</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${ongoingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Date</Label>
                                            <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>{ongoingBooking.date}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${ongoingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Start Time</Label>
                                            <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>{ongoingBooking.startTime}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${ongoingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>End Time</Label>
                                            <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>{ongoingBooking.endTime}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${ongoingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Customer Email</Label>
                                            <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>{ongoingBooking.userEmail}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${ongoingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Amount</Label>
                                            <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>{ongoingBooking.amount} CAD</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`service-${ongoingBooking.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Note</Label>
                                            {(ongoingBooking.note === null || ongoingBooking.note === "") ?  
                                                <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>Customer doesn't require extra attention.</span>
                                                :
                                                <span id={`service-${ongoingBooking.id}`} style={{ fontSize: '0.875rem' }}>{ongoingBooking.note}</span>
                                            }
                                        </div>
                                    </div>
                                </CardContent>
                                <CardFooter>
                                    <div className="flex items-center space-x-4">
                                        <Dialog
                                            open={isCompletedConfirmationDialogOpen}
                                            onOpenChange={setCompletedConfirmationDialogOpen}
                                        >
                                            <DialogTrigger asChild>
                                                <Button type="button">Set Completed</Button>
                                            </DialogTrigger>
                                            <DialogContent className="sm:max-w-[425px]">
                                                <DialogHeader>
                                                    <DialogTitle>Confirmation</DialogTitle>
                                                </DialogHeader>
                                                <form
                                                    onSubmit={handleSubmit((formData) =>
                                                        handleCompletedStatus({
                                                            ongoingBooking,
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
                                                                Are you sure you want to change the status to Completed?
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
                        ))}
                    </div>
                </div>
            }
        </div>
    )
}

export default ViewOngoingBookings;
