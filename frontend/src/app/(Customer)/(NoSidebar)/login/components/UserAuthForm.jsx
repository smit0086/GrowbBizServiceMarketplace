"use client";

import * as React from "react";
import { signIn } from "next-auth/react";
import { useForm, Controller } from "react-hook-form";
import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ERROR_MESSAGE, REGEX, ROLES } from "@/lib/constants";

export function UserAuthForm({ className, ...props }) {
    const [isLoading, setIsLoading] = React.useState(false);
    const {
        handleSubmit,
        control,
        formState: { errors },
    } = useForm();

    async function onSubmit(data) {
        setIsLoading(true);
        await signIn("credentials", {
            email: data.email,
            password: data.password,
            role: ROLES.CUSTOMER,
            redirect: true,
            callbackUrl: props.callbackUrl ?? "/",
        });
        setIsLoading(false);
    }

    return (
        <div className={cn("grid gap-6", className)} {...props}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="grid gap-2">
                    <div className="grid gap-2">
                        <Label htmlFor="email">Email</Label>
                        <Controller
                            name="email"
                            control={control}
                            defaultValue=""
                            rules={{
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                                pattern: {
                                    value: REGEX.EMAIL,
                                    message: ERROR_MESSAGE.INVALID_EMAIL,
                                },
                            }}
                            render={({ field }) => (
                                <Input
                                    {...field}
                                    id="email"
                                    placeholder="name@example.com"
                                    type="email"
                                    autoCapitalize="none"
                                    autoComplete="email"
                                    autoCorrect="off"
                                    disabled={isLoading}
                                />
                            )}
                        />
                        {errors.email && (
                            <span className="text-xs text-destructive">
                                {errors.email.message}
                            </span>
                        )}
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="password">Password</Label>
                        <Controller
                            control={control}
                            name="password"
                            defaultValue=""
                            rules={{
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                            }}
                            render={({ field }) => (
                                <Input
                                    {...field}
                                    id="password"
                                    placeholder="password"
                                    type="password"
                                    disabled={isLoading}
                                />
                            )}
                        />
                        {errors.password && (
                            <span className="text-xs text-destructive">
                                {errors.password.message}
                            </span>
                        )}
                    </div>
                    <Button disabled={isLoading}>
                        {isLoading && (
                            <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                        )}
                        Sign In
                    </Button>
                </div>
            </form>
        </div>
    );
}
