"use client";
import React from "react";
import { useSession } from "next-auth/react";
import { useForm } from "react-hook-form";
import * as z from "zod";
import { ExclamationTriangleIcon } from "@radix-ui/react-icons";
import { zodResolver } from "@hookform/resolvers/zod";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import { ERROR_MESSAGE } from "@/lib/constants";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";

const formSchema = z.object({
    businessName: z
        .string({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(1),
    businessCategory: z
        .string({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(1),
    verificationDocuments: z
        .any()
        .refine((val) => !!val, ERROR_MESSAGE.REQUIRED),
});
const BusinessForm = ({
    categories,
    title,
    subtitle,
    buttonText,
    databaseMutator,
    failureReason,
    formDefaults,
    businessId,
}) => {
    const session = useSession();
    const [isLoading, setIsLoading] = React.useState(false);
    const form = useForm({
        resolver: zodResolver(formSchema),
        defaultValues: {
            businessName: formDefaults?.businessName || "",
            businessCategory: formDefaults?.businessCategory || "",
        },
    });
    const onSubmit = async (data) => {
        setIsLoading(true);
        const resp = await databaseMutator(
            data.businessName,
            session.data.user.email,
            parseInt(data.businessCategory, 10),
            session.data.user.role,
            data.verificationDocuments,
            session.data.apiToken,
            businessId
        );
        if (resp) {
            window.location.reload();
        }
        setIsLoading(false);
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
                            {failureReason && (
                                <Alert variant="destructive">
                                    <ExclamationTriangleIcon className="h-4 w-4" />
                                    <AlertTitle>
                                        Disapproval reason:{" "}
                                    </AlertTitle>
                                    <AlertDescription>
                                        {failureReason}
                                    </AlertDescription>
                                </Alert>
                            )}
                            <div className="grid gap-2">
                                <FormField
                                    control={form.control}
                                    name="businessName"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Business name</FormLabel>
                                            <FormControl>
                                                <Input
                                                    placeholder="business name"
                                                    {...field}
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
                                    name="businessCategory"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>Category</FormLabel>
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
                                                    {categories &&
                                                        categories.map(
                                                            (category) => (
                                                                <SelectItem
                                                                    value={`${category.categoryID}`}
                                                                    key={
                                                                        category.categoryID
                                                                    }
                                                                >
                                                                    {
                                                                        category.name
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
                                    name="verificationDocuments"
                                    render={({
                                        field: { onChange },
                                        ...field
                                    }) => (
                                        <FormItem>
                                            <FormLabel>
                                                Verification documents
                                            </FormLabel>
                                            <FormControl>
                                                <Input
                                                    type="file"
                                                    placeholder="Verification documents"
                                                    {...field}
                                                    onChange={(event) =>
                                                        onChange(
                                                            event.target
                                                                .files[0]
                                                        )
                                                    }
                                                />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    )}
                                />
                            </div>
                        </CardContent>

                        <CardFooter>
                            <Button
                                className="w-full"
                                type="submit"
                                disabled={isLoading}
                            >
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

export default BusinessForm;
