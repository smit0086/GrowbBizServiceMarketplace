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

const ServiceForm = ({
    predefinedServices,
    cancelButton,
    services,
    setServices,
    setRenderServiceForm,
    formDefaults,
    setFormDefaults,
    title,
    subtitle,
    buttonText,
}) => {
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

    const onSubmit = async (data) => {
        setLoading(true);
        console.log(data);
        if (formDefaults === undefined || formDefaults === null) {
            const maxServiceId = services.reduce((maxId, service) => {
                return Math.max(maxId, parseInt(service.serviceId, 10));
            }, 0);
            const newService = {
                serviceId: (maxServiceId + 1).toString(),
                serviceName: data.serviceName,
                servicePrice: data.price,
                timeRequired: data.timeRequired,
                description: data.description,
            };
            setServices((prevServices) => [...prevServices, newService]);
            setRenderServiceForm(false);
            console.log("New service added:");
            console.log(newService);
        } else {
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
            console.log(
                "Service updated with serviceId:",
                formDefaults.serviceId
            );
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
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Service</FormLabel>
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
                                                    {predefinedServices &&
                                                        predefinedServices.map(
                                                            (
                                                                predefinedService
                                                            ) => (
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
                                    )}
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
                            {cancelButton && (
                                <Button
                                    variant="destructive"
                                    type="button"
                                    onClick={() => {
                                        setRenderServiceForm(false);
                                    }}
                                >
                                    Cancel
                                </Button>
                            )}
                            <Button type="submit" disabled={isLoading}>
                                {isLoading && (
                                    <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                )}
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
