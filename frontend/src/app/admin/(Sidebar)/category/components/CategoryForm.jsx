"use client";

import * as React from "react";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, Controller } from "react-hook-form";
import { addCategory } from "@/services/categoriesServices";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import { ERROR_MESSAGE } from "@/lib/constants";

const formSchema = z.object({
    name: z
    .string({
        required_error: ERROR_MESSAGE.REQUIRED,
    })
    .min(1)
});

export function CategoryForm({ className, ...props }) {
    const session = useSession();
    const router = useRouter();
    console.log(router);
    const [isLoading, setIsLoading] = React.useState(false);
    const {
        handleSubmit,
        control,
        formState: { errors },
    } = useForm();

    const form = useForm({
        resolver: zodResolver(formSchema),
    });

    async function onSubmit(data) {
        setIsLoading(true);
        await addCategory(session.data.apiToken, data.name, data.tax);
        window.location.href = `/admin/category`;
    }

    return (
        <div className="grid h-screen">
        <Card className="w-[500px] place-self-center">
            <Form {...form}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <CardHeader className="space-y-1">
                        <CardTitle className="text-2xl">Add Category Form</CardTitle>
                        <CardDescription>This form allows a creation of a main business category</CardDescription>
                    </CardHeader>
                    <CardContent className="grid gap-4">        
                        <div className="grid gap-2">
                            <FormField
                                control={control}
                                name="name"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Category name</FormLabel>
                                        <FormControl>
                                            <Input
                                                placeholder="E.g: House Management Services..."
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
                                control={control}
                                name="tax"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>Tax</FormLabel>
                                        <FormControl>
                                            <Input
                                                placeholder="Tax in %"
                                                {...field}
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
                            Add Category
                        </Button>
                    </CardFooter>
                </form>
            </Form>
        </Card>
        </div>
    );
}
