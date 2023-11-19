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
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
  } from "@/components/ui/select"
import { useForm, Controller } from "react-hook-form";
import { updateSubCategory } from "@/services/subCategoriesServices";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { ERROR_MESSAGE } from "@/lib/constants";

const formSchema = z.object({
    name: z
    .string({
        required_error: ERROR_MESSAGE.REQUIRED,
    })
    .min(1)
});

export function UpdateSubForm({className, subcategoryID, categories, categoryID, ...props}) {
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
    //     defaultValues: {
    //         name: formDefaults?.name || "",
    //     },
    });

    async function onSubmit(data) {
        setIsLoading(true);
        await updateSubCategory(session.data.apiToken, subcategoryID, data.name, data.categoryID)
        window.location.href = `/admin/category/${categoryID}/subcategory`;
    }

    return (
            <div className="grid h-screen">
            <Card className="w-[500px] place-self-center">
                <Form {...form}>
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <CardHeader className="space-y-1">
                            <CardTitle className="text-2xl">Add Subcategory Form</CardTitle>
                            <CardDescription>This form allows a creation of a subcategory</CardDescription>
                        </CardHeader>
                        <CardContent className="grid gap-4">        
                            <div className="grid gap-2">
                                <FormField
                                    control={control}
                                    name="name"
                                    render={({ field }) => (
                                        <FormItem>
                                            <FormLabel>SubCategory name</FormLabel>
                                            <FormControl>
                                                <Input
                                                    placeholder="subcategory name"
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
                                    name="categoryID"
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
                                                    {categories.map(
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
                                Edit Subcategory
                            </Button>
                        </CardFooter>
                    </form>
                </Form>
            </Card>
        </div>    
    );
}
