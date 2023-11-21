"use client";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
} from "@/components/ui/dialog";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { ERROR_MESSAGE } from "@/lib/constants";
import { cn } from "@/lib/utils";
import { zodResolver } from "@hookform/resolvers/zod";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import moment from "moment";
import React, { useRef, useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { StripeContainer } from "./StripeContainer";

const formSchema = z.object({
    slotDate: z.date({
        required_error: ERROR_MESSAGE.REQUIRED,
    }),
    slotTime: z.string({
        required_error: ERROR_MESSAGE.REQUIRED,
    }),
    note: z.string().optional(),
});

const SlotSelection = ({
    form,
    availableTimeSlots,
    availableDates,
    dateLabel,
    slotLabel,
}) => {
    const { watch } = form;
    const selectedDateState = watch("slotDate");
    const selectedDate = selectedDateState
        ? moment(selectedDateState).format("YYYY-MM-DD")
        : null;
    console.log({ selectedDate });
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
        <>
            <div className="grip gap-2  my-2">
                <FormField
                    control={form.control}
                    name="slotDate"
                    render={({ field }) => (
                        <FormItem className="flex flex-col">
                            <FormLabel className="text-xl font-semibold">
                                {dateLabel}
                            </FormLabel>
                            <Popover>
                                <PopoverTrigger asChild>
                                    <FormControl>
                                        <Button
                                            type="button"
                                            variant={"outline"}
                                            className={cn(
                                                "w-full pl-3 text-left font-normal",
                                                !field.value &&
                                                    "text-muted-foreground"
                                            )}
                                        >
                                            {field.value ? (
                                                format(field.value, "PPP")
                                            ) : (
                                                <span>Pick a date</span>
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
                                            field.onChange(date);
                                        }}
                                        disabled={isDateDisabled}
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
                <div className="grid gap-2 mb-2">
                    <FormField
                        control={form.control}
                        name="slotTime"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel className="text-xl font-semibold">
                                    {slotLabel}
                                </FormLabel>
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
                                            availableTimeSlots[selectedDate] &&
                                            availableTimeSlots[
                                                selectedDate
                                            ].map((timeSlot) => (
                                                <SelectItem
                                                    value={`${timeSlot}`}
                                                    key={timeSlot}
                                                >
                                                    {timeSlot}
                                                </SelectItem>
                                            ))}
                                    </SelectContent>
                                </Select>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                </div>
            )}
        </>
    );
};
const ServiceCheckoutForm = ({
    service,
    availableTimeSlots,
    availableDates,
}) => {
    const form = useForm({
        resolver: zodResolver(formSchema),
    });
    const submitRef = useRef(null);
    const formDataRef = useRef(null);
    const [openCheckoutDialog, setOpenCheckoutDialog] = useState(false);
    const [showPaymentForm, setShowPaymentForm] = useState(false);
    const onSubmit = async (data) => {
        const formattedDate = format(data.slotDate, "yyyy-MM-dd");
        const [startTimeStr, endTimeStr] = data.slotTime.split(" - ");
        const startTime = startTimeStr.replace(" AM", "");
        const endTime = endTimeStr.replace(" AM", "");
        formDataRef.current = {
            serviceId: service.serviceId,
            date: formattedDate,
            note: data.note,
            startTime,
            endTime,
        };
        setShowPaymentForm(true);
    };
    const closeDialog = () => {
        setOpenCheckoutDialog(false);
    };
    return (
        <div className="max-w-[300px]">
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)}>
                    <SlotSelection
                        form={form}
                        availableTimeSlots={availableTimeSlots}
                        availableDates={availableDates}
                        dateLabel="Available Dates"
                        slotLabel="Available Slots"
                    />
                    <Button
                        type="button"
                        className="my-6"
                        onClick={() => setOpenCheckoutDialog(true)}
                    >
                        Book
                    </Button>
                    <button
                        ref={submitRef}
                        type="submit"
                        className="hidden"
                    ></button>
                    <Dialog
                        open={openCheckoutDialog}
                        onOpenChange={setOpenCheckoutDialog}
                    >
                        <DialogContent className="sm:max-w-[425px] min-h-[433px]">
                            <DialogHeader>
                                <DialogTitle className="text-xl font-semibold">
                                    {!showPaymentForm
                                        ? "Bookings details"
                                        : "Payment details"}
                                </DialogTitle>
                            </DialogHeader>
                            {!showPaymentForm && (
                                <>
                                    <SlotSelection
                                        form={form}
                                        availableTimeSlots={availableTimeSlots}
                                        availableDates={availableDates}
                                        dateLabel="Seletecd Date"
                                        slotLabel="Selected Slot"
                                    />
                                    <div className="grid gap-2">
                                        <FormField
                                            control={form.control}
                                            name="note"
                                            render={({ field }) => (
                                                <FormItem>
                                                    <FormLabel className="text-xl font-semibold">
                                                        Note
                                                    </FormLabel>
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
                                    <Button
                                        onClick={() => {
                                            submitRef.current.click();
                                        }}
                                    >
                                        Proceed to payment
                                    </Button>
                                </>
                            )}
                            {showPaymentForm && (
                                <StripeContainer
                                    formData={formDataRef.current}
                                    closeDialog={closeDialog}
                                />
                            )}
                        </DialogContent>
                    </Dialog>
                </form>
            </Form>
        </div>
    );
};

export default ServiceCheckoutForm;
