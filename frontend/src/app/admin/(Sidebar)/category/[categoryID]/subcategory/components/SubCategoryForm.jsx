"use client";

import * as React from "react";

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
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import { addSubCategory } from "@/services/subCategoriesServices";
import { ERROR_MESSAGE } from "@/lib/constants";

const formSchema = z.object({
    name: z
        .string({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(1),
});
export function SubCategoryForm({ className, categoryID, ...props }) {
    const session = useSession();
    const router = useRouter();
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
        await addSubCategory(session.data.apiToken, data.name, categoryID);
        window.location.href = `/admin/category/${categoryID}/subcategory`;
    }

    return (
        <Card className="w-[500px] my-6">
            <Form {...form}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <CardContent className="grid gap-4 pt-6">
                        <div className="grid gap-2">
                            <FormField
                                control={control}
                                name="name"
                                render={({ field }) => (
                                    <FormItem>
                                        <FormLabel>SubCategory name</FormLabel>
                                        <FormControl>
                                            <Input
                                                placeholder="E.g: Carpet Cleaning..."
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
                            Add Subcategory
                        </Button>
                    </CardFooter>
                </form>
            </Form>
        </Card>
    );
}
