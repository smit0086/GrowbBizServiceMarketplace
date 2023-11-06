"use client"

import { zodResolver } from "@hookform/resolvers/zod"
import * as z from "zod"

import { Button } from "@/components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage
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
import { useState } from "react";
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

const formSchema = z.object({
    slotDate: z.date({
        required_error: ERROR_MESSAGE.REQUIRED
    }),
    slotTime: z.string({
        required_error: ERROR_MESSAGE.REQUIRED
    }),
    note: z.string().optional()
})

const ServiceBookingForm = ({ service, availableDates, availableTimeSlots }) => {
    const form = useForm({
        resolver: zodResolver(formSchema)
    });
    const { reset } = useForm();
    const [selectedDate, setSelectedDate] = useState();
    const [isLoading, setLoading] = useState(false);

    const onSubmit = async (data) => {
        setLoading(true);
        console.log(data);
        setLoading(false);
    }

    const handleDateChange = (date) => {
        setSelectedDate(date);
    }

    return (
        <div className="grid h-screen">
            <Card className="w-[500px] place-self-center">
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)}>
                        <CardHeader className="space-y-1">
                            <CardTitle className="text-2xl">Service Booking</CardTitle>
                            <CardDescription>Please book a service.</CardDescription>
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
                                    {service.servicePrice} (Base Price) + {service.serviceTax}% (Tax)
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
                                                                !field.value && "text-muted-foreground"
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
                                                <PopoverContent className="w-auto p-0" align="start">
                                                    <Calendar
                                                        mode="single"
                                                        selected={field.value}
                                                        onSelect={(date) => {
                                                            field.onChange(date);
                                                            handleDateChange(date);
                                                        }}
                                                        disabled={(date) =>
                                                            date < new Date("1900-01-01") || !availableDates.includes(date.toISOString().split('T')[0])
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
                                                            availableTimeSlots.map(
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
                        <CardFooter className="flex justify-between">
                            <Button variant="destructive" type="button">Cancel</Button>
                            <Button
                                type="submit"
                                disabled={isLoading}
                            >
                                {isLoading && (
                                    <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                )}
                                Submit
                            </Button>
                        </CardFooter>
                    </form>
                </Form>
            </Card>
        </div>
    );
}

export default ServiceBookingForm;