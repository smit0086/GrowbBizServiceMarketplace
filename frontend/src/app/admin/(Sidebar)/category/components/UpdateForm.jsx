"use client";

import * as React from "react";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useForm, Controller } from "react-hook-form";
import { updateCategory } from "@/services/categoriesServices";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";


export function UpdateForm({className, category_id , ...props}) {
    const session = useSession();
    const router = useRouter();
    // const {category_id} = router.query
    console.log(category_id);
    const [isLoading, setIsLoading] = React.useState(false);
    const {
        handleSubmit,
        control,
        formState: { errors },
    } = useForm();

    async function onSubmit(data) {
        setIsLoading(true);
        await updateCategory(session.data.apiToken, category_id, data.name, data.tax)
        router.push("/admin/category");
    }

    return (
        <div className={cn("grid gap-6", className)} {...props}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="grid gap-2">
                    <div className="grid gap-2">
                        <Label htmlFor="name">Updated Category Name</Label>
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
                        <Label htmlFor="tax">Updated Tax Rate %</Label>
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
                        Update Category
                    </Button>
                </div>
            </form>
        </div>
    );
}
