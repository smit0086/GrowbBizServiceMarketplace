"use client";

import * as React from "react";
import { useForm } from "react-hook-form";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ERROR_MESSAGE, REGEX } from "@/lib/constants";

export function UserAuthForm({ className, ...props }) {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm();
    const onSubmit = (data) => {
        console.log(data);
        setIsLoading(true);

        setTimeout(() => {
            setIsLoading(false);
        }, 3000);
    };
    const [isLoading, setIsLoading] = React.useState(false);

    return (
        <div className={cn("grid gap-6", className)} {...props}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="grid gap-2">
                    <div className="grid gap-2">
                        <Label htmlFor="email">Email</Label>
                        <Input
                            id="email"
                            placeholder="name@example.com"
                            type="text"
                            autoCapitalize="none"
                            autoComplete="email"
                            autoCorrect="off"
                            disabled={isLoading}
                            {...register("email", {
                                pattern: {
                                    value: REGEX.EMAIL,
                                    message: ERROR_MESSAGE.INVALID_EMAIL,
                                },
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                            })}
                        />
                        <span className="text-xs text-destructive">
                            {errors.email?.message}
                        </span>
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="password">Password</Label>
                        <Input
                            id="password"
                            type="password"
                            placeholder="password"
                            disabled={isLoading}
                            {...register("password", {
                                pattern: {
                                    value: REGEX.PASSWORD_POLICY,
                                    message: ERROR_MESSAGE.INVALID_PASSWORD,
                                },
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                            })}
                        />
                        <span className="text-xs text-destructive">
                            {errors.password?.message}
                        </span>
                    </div>
                    <Button disabled={isLoading} type="submit">
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
