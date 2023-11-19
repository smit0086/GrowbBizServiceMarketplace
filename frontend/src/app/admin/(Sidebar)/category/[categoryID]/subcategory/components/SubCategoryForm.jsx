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
import * as z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm, Controller } from "react-hook-form";
import { getAllCategories } from "@/services/categoriesServices";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import { addSubCategory } from "@/services/subCategoriesServices";
import { ERROR_MESSAGE } from "@/lib/constants";

const formSchema = z.object({
        name: z
        .string({
            required_error: ERROR_MESSAGE.REQUIRED,
        })
        .min(1)
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
    //     defaultValues: {
    //         name: formDefaults?.name || "",
    //     },
    });

    async function onSubmit(data) {
        setIsLoading(true);
        await addSubCategory(session.data.apiToken, data.name, categoryID);
        // router.push("/admin/category");
        window.location.href = `/admin/category/${categoryID}/subcategory`;
    }

    return (
        <div className="grid h-screen">
        <Card className="w-[500px] place-self-center">
            <Form {...form}>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <CardHeader className="space-y-1">
                        <CardTitle className="text-2xl">Update Subcategory Form</CardTitle>
                        <CardDescription>This form allows updating a subcategory</CardDescription>
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
                                                placeholder="E.g: Carpet Cleaning..."
                                                {...field}
                                            />
                                        </FormControl>
                                        <FormMessage />
                                    </FormItem>
                                )}
                            />
                        </div>
                        {/* <div className="grid gap-2">
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
                        </div> */}
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
    </div>    
    );
}
