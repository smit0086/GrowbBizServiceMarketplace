"use client";

import { zodResolver } from "@hookform/resolvers/zod";
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
import { Input } from "@/components/ui/input";
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
import { addService } from "@/services/servicesService";
import { updateService } from "@/services/servicesService";
import { Icons } from "@/components/icons";

const formSchema = z.object({
    serviceName: z
        .string({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(1),
    price: z
        .number({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(0),
    timeRequired: z
        .number({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(0),
    description: z
        .string({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(1),
});

const ServiceForm = ({ authSession, predefinedServices, cancelButton, services, setServices, setRenderServiceForm, formDefaults, setFormDefaults, title, subtitle, buttonText, businessId }) => {
    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            serviceName: formDefaults?.serviceName,
            price: formDefaults?.servicePrice,
            timeRequired: formDefaults?.timeRequired,
            description: formDefaults?.description,
        },
    });
    const [isLoading, setLoading] = useState(false);
    const [subCategoryId, setSubCategoryId] = useState();

    const onSubmit = async (data) => {
        setLoading(true);
        const hours = Math.floor(data.timeRequired / 60);
        const remainingMinutes = data.timeRequired % 60;
        const formattedHours = hours.toString().padStart(2, '0');
        const formattedMinutes = remainingMinutes.toString().padStart(2, '0');
        if (formDefaults === undefined || formDefaults === null) {
            const addedService = await addService(authSession.apiToken, data.serviceName, data.description, `${formattedHours}:${formattedMinutes}`, businessId, subCategoryId);
            const newService = {
                serviceId: addedService.service_id,
                serviceName: data.serviceName,
                servicePrice: data.price,
                timeRequired: data.timeRequired,
                description: data.description,
            };
            setServices((prevServices) => [...prevServices, newService]);
            setRenderServiceForm(false);
        }
        else {
            const updatedService = await updateService(authSession.apiToken, formDefaults.serviceId, data.serviceName, data.description, `${formattedHours}:${formattedMinutes}`, businessId, subCategoryId);
            setServices((prevServices) =>
                prevServices.map((service) => {
                    if (service.serviceId === formDefaults.serviceId) {
                        return {
                            ...service,
                            serviceName: data.serviceName,
                            servicePrice: data.price,
                            timeRequired: data.timeRequired,
                            description: data.description,
                        };
                    }
                    return service;
                })
            );
            setFormDefaults(null);
            setRenderServiceForm(false);
        }

        setLoading(false);
    };

    return (
        <div className="grid h-screen">
            <Card className="w-[500px] place-self-center">
                <Form {...form}>
                    <form onSubmit={form.handleSubmit(onSubmit)}>
                        <CardHeader className="space-y-1">
                            <CardTitle className="text-2xl">{title}</CardTitle>
                            <CardDescription>{subtitle}</CardDescription>
                        </CardHeader>
                        <CardContent className="grid gap-4">
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="serviceName"
                                    render={({ field }) => {
                                        const defaultValue = field.value;
                                        const selectedService = predefinedServices.find(service => service.predefinedServiceName === defaultValue);

                                        if (selectedService) {
                                            setSubCategoryId(selectedService.predefinedServiceId);
                                        }

                                        return (
                                            <FormItem>
                                                <FormLabel>Service</FormLabel>
                                                <Select
                                                    onValueChange={(e) => {
                                                        field.onChange(e);
                                                        const selectedServiceId = predefinedServices.find(service => service.predefinedServiceName === e)?.predefinedServiceId;
                                                        setSubCategoryId(selectedServiceId);
                                                    }}
                                                    defaultValue={field.value}
                                                >
                                                    <FormControl>
                                                        <SelectTrigger>
                                                            <SelectValue placeholder="Select" />
                                                        </SelectTrigger>
                                                    </FormControl>
                                                    <SelectContent position="popper">
                                                        {predefinedServices &&
                                                            predefinedServices.map(
                                                                (predefinedService) => (
                                                                    <SelectItem
                                                                        value={`${predefinedService.predefinedServiceName}`}
                                                                        key={
                                                                            predefinedService.predefinedServiceId
                                                                        }
                                                                    >
                                                                        {
                                                                            predefinedService.predefinedServiceName
                                                                        }
                                                                    </SelectItem>
                                                                )
                                                            )}
                                                    </SelectContent>
                                                </Select>
                                                <FormMessage />
                                            </FormItem>
                                        )
                                    }
                                    }
                                />
                            </div>
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="price"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>
                                                Pricer per hour
                                            </FormLabel>
                                            <FormControl>
                                                <Input
                                                    placeholder="price"
                                                    {...field}
                                                    type="number"
                                                    defaultValue={field.value}
                                                    step="0.01"
                                                    min={0}
                                                    onChange={(e) => {
                                                        field.onChange(
                                                            parseFloat(
                                                                e.target.value
                                                            )
                                                        );
                                                    }}
                                                />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="timeRequired"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>
                                                Time Required (Minutes)
                                            </FormLabel>
                                            <FormControl>
                                                <Input
                                                    placeholder="time required"
                                                    {...field}
                                                    type="number"
                                                    defaultValue={field.value}
                                                    min={0}
                                                    onChange={(e) => {
                                                        field.onChange(
                                                            parseInt(
                                                                e.target.value
                                                            )
                                                        );
                                                    }}
                                                />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="description"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Description</FormLabel>
                                            <FormControl>
                                                <Textarea
                                                    placeholder="provide a description about service"
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
                            {cancelButton &&
                                <Button variant="destructive" type="button" onClick={() => { setRenderServiceForm(false) }}>Cancel</Button>
                            }
                            <Button
                                type="submit"
                                disabled={isLoading}
                            >
                                {/* {isLoading && (
                                    <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                )} */}
                                {buttonText}
                            </Button>
                        </CardFooter>
                    </form>
                </Form>
            </Card>
        </div>
    );
};

export default ServiceForm;
