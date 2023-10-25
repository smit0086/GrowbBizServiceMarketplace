"use client";

import * as React from "react";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useForm, Controller } from "react-hook-form";
import { addCategory } from "@/services/categoriesServices";
import { useSession } from "next-auth/react";


export function CategoryForm({ className, ...props }) {

    const session =  useSession();
    console.log(session.data.apiToken);
    const [isLoading, setIsLoading] = React.useState(false);
    const {
        handleSubmit,
        control,
        formState: { errors },
    } = useForm();

    async function onSubmit(data) {
        setIsLoading(true);
        await addCategory(session.data.apiToken, data.name, data.tax);
        setIsLoading(false);
    }

    return (
        <div className={cn("grid gap-6", className)} {...props}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="grid gap-2">
                    <div className="grid gap-2">
                        <Label htmlFor="name">Category</Label>
                        <Controller
                            name="name"
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <Input
                                    {...field}
                                    id="name"
                                    placeholder="E.g: House Management Services..."
                                    type="text"
                                    autoCapitalize="none"
                                    autoCorrect="off"
                                    disabled={isLoading}
                                />
                            )}
                        />
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="tax">Tax %</Label>
                        <Controller
                            control={control}
                            name="tax"
                            defaultValue=""
                            render={({ field }) => (
                                <Input
                                    {...field}
                                    id="tax"
                                    placeholder="Tax in %"
                                    type="text"
                                    disabled={isLoading}
                                />
                            )}
                        />
                    </div>
                    <Button disabled={isLoading}>
                        {isLoading && (
                            <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                        )}
                        Add Category
                    </Button>
                </div>
            </form>
        </div>
    );
}

