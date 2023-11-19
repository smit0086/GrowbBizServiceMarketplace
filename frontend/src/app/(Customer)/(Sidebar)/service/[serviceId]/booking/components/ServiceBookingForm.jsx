"use client";

import { zodResolver } from "@hookform/resolvers/zod";
import { useRef, useState } from "react";
import * as z from "zod";

import { Button } from "@/components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import { useForm } from "react-hook-form";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import { ERROR_MESSAGE } from "@/lib/constants";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { Calendar } from "@/components/ui/calendar";
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "@/lib/utils";
import { CalendarIcon } from "lucide-react";
import { format } from "date-fns";
import { Icons } from "@/components/icons";
import { bookService } from "@/services/bookingService";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
} from "@/components/ui/dialog";
import { StripeContainer } from "./StripeContainer";
import moment from "moment";

const formSchema = z.object({
    slotDate: z.date({
        required_error: ERROR_MESSAGE.REQUIRED,
    }),
    slotTime: z.string({
        required_error: ERROR_MESSAGE.REQUIRED,
    }),
    note: z.string().optional(),
});

const ServiceBookingForm = ({
    authSession,
    service,
    tax,
    availableDates,
    availableTimeSlots,
}) => {
    const form = useForm({
        resolver: zodResolver(formSchema),
    });
    const { reset, getValues } = useForm();
    const [selectedDate, setSelectedDate] = useState();
    const [isLoading, setLoading] = useState(false);
    const [paymentDialogOpen, setPaymentDialogOpen] = useState(false);
    const formDataRef = useRef(null);

    const closeDialog = () => {
        setPaymentDialogOpen(false);
    };

    const onSubmit = async (data) => {
        setLoading(true);
        setPaymentDialogOpen(true);

        const formattedDate = format(data.slotDate, "yyyy-MM-dd");
        const [startTimeStr, endTimeStr] = data.slotTime.split(" - ");
        const startTime = startTimeStr.replace(" AM", "");
        const endTime = endTimeStr.replace(" AM", "");
        const totalAmount = parseFloat(service.price) + parseFloat(tax);
        formDataRef.current = {
            serviceId: service.serviceId,
            date: formattedDate,
            note: data.note,
            startTime,
            endTime,
        };

        setLoading(false);
    };

    const handleDateChange = (date) => {
        setSelectedDate(moment(date).format("YYYY-MM-DD"));
    };

    const isDateDisabled = (date) => {
        const momentDateObj = moment(date);
        const formattedDate = momentDateObj.format("YYYY-MM-DD");
        const todayMomentDateObj = moment();

        return (
            !availableDates.includes(formattedDate) ||
            momentDateObj.isBefore(todayMomentDateObj, "date")
        );
    };

    return (
        <div className="grid h-screen">
            <Card className="w-[500px] place-self-center">
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)}>
                        <CardHeader className="space-y-1">
                            <CardTitle className="text-2xl">
                                Service Booking
                            </CardTitle>
                            <CardDescription>
                                Please book a service.
                            </CardDescription>
                        </CardHeader>
                        <CardContent className="grid gap-4">
                            <div className="grid gap-2">
                                <FormLabel>Service Name</FormLabel>
                                <div className="text-gray-600">
                                    {service.serviceName}
                                </div>
                            </div>
                            <div className="grid gap-2">
                                <FormLabel>Service Price</FormLabel>
                                <div className="text-gray-600">
                                    {service.price} (Base Price) + {tax}% (Tax)
                                </div>
                            </div>
                            <div className="grip gap-2">
                                <FormField
                                    control={form.control}
                                    name="slotDate"
                                    render={({ field }) => (
                                        <FormItem className="flex flex-col">
                                            <FormLabel>Slot Date</FormLabel>
                                            <Popover>
                                                <PopoverTrigger asChild>
                                                    <FormControl>
                                                        <Button
                                                            type="button"
                                                            variant={"outline"}
                                                            className={cn(
                                                                "w-[240px] pl-3 text-left font-normal",
                                                                !field.value &&
                                                                    "text-muted-foreground"
                                                            )}
                                                        >
                                                            {field.value ? (
                                                                format(
                                                                    field.value,
                                                                    "PPP"
                                                                )
                                                            ) : (
                                                                <span>
                                                                    Pick a date
                                                                </span>
                                                            )}
                                                            <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                                                        </Button>
                                                    </FormControl>
                                                </PopoverTrigger>
                                                <PopoverContent
                                                    className="w-auto p-0"
                                                    align="start"
                                                >
                                                    <Calendar
                                                        mode="single"
                                                        selected={field.value}
                                                        onSelect={(date) => {
                                                            field.onChange(
                                                                date
                                                            );
                                                            handleDateChange(
                                                                date
                                                            );
                                                        }}
                                                        disabled={
                                                            isDateDisabled
                                                        }
                                                        initialFocus
                                                    />
                                                </PopoverContent>
                                            </Popover>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                            {selectedDate && (
                                <div className="grid gap-2">
                                    <FormField
                                        control={form.control}
                                        name="slotTime"
                                        render={({ field }) => (
                                            <FormItem>
                                                <FormLabel>Slot Time</FormLabel>
                                                <Select
                                                    onValueChange={(e) => {
                                                        field.onChange(e);
                                                    }}
                                                    defaultValue={field.value}
                                                >
                                                    <FormControl>
                                                        <SelectTrigger>
                                                            <SelectValue placeholder="Select" />
                                                        </SelectTrigger>
                                                    </FormControl>
                                                    <SelectContent position="popper">
                                                        {availableTimeSlots &&
                                                            availableTimeSlots[
                                                                selectedDate
                                                            ] &&
                                                            availableTimeSlots[
                                                                selectedDate
                                                            ].map(
                                                                (timeSlot) => (
                                                                    <SelectItem
                                                                        value={`${timeSlot}`}
                                                                        key={
                                                                            timeSlot
                                                                        }
                                                                    >
                                                                        {
                                                                            timeSlot
                                                                        }
                                                                    </SelectItem>
                                                                )
                                                            )}
                                                    </SelectContent>
                                                </Select>
                                                <FormMessage />
                                            </FormItem>
                                        )}
                                    />
                                </div>
                            )}
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="note"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Note</FormLabel>
                                            <FormControl>
                                                <Textarea
                                                    placeholder="provide any additional notes that require special attention"
                                                    {...field}
                                                />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                        </CardContent>
                        <CardFooter className="flex justify-end">
                            <Dialog
                                open={paymentDialogOpen}
                                onOpenChange={setPaymentDialogOpen}
                            >
                                <Button type="submit">
                                    {isLoading && (
                                        <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                    )}
                                    Book
                                </Button>
                                <DialogContent className="sm:max-w-[425px] min-h-[433px]">
                                    <DialogHeader>
                                        <DialogTitle>Payment</DialogTitle>
                                    </DialogHeader>
                                    <StripeContainer
                                        formData={formDataRef.current}
                                        closeDialog={closeDialog}
                                    />
                                </DialogContent>
                            </Dialog>
                        </CardFooter>
                    </form>
                </Form>
            </Card>
        </div>
    );
};

export default ServiceBookingForm;
