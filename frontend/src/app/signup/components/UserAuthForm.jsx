"use client";

import * as React from "react";
import { signIn } from "next-auth/react";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useForm, Controller } from "react-hook-form";
import { ERROR_MESSAGE, REGEX } from "@/lib/constants";

const signupCustomer = async (firstName, lastName, email, password) => {
    const body = JSON.stringify({
        email,
        firstName,
        lastName,
        password,
        role: "CUSTOMER",
    });
    const resp = await (
        await fetch("/api/auth/signup", {
            method: "post",
            body,
            headers: {
                "Content-Type": "application/json",
            },
        })
    ).json();
    return resp;
};

export function UserAuthForm({ className, ...props }) {
    const [isLoading, setIsLoading] = React.useState(false);
    const {
        handleSubmit,
        control,
        formState: { errors },
    } = useForm();

    async function onSubmit(data) {
        setIsLoading(true);

        const user = await signupCustomer(
            data.firstName,
            data.lastName,
            data.email,
            data.password
        );
        await signIn("credentials", {
            email: data.email,
            password: data.password,
            role: "CUSTOMER",
            redirect: true,
            callbackUrl: props.callbackUrl ?? "/",
        });

        setTimeout(() => {
            setIsLoading(false);
        }, 3000);
    }

    return (
        <div className={cn("grid gap-6", className)} {...props}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="grid gap-2">
                    <div className="grid gap-2">
                        <Label htmlFor="firstName">First Name</Label>
                        <Controller
                            name="firstName"
                            control={control}
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
                                    id="firstName"
                                    placeholder="first name"
                                    type="text"
                                    disabled={isLoading}
                                />
                            )}
                        />
                        {errors.firstName && (
                            <span className="text-xs text-destructive">
                                {errors.firstName.message}
                            </span>
                        )}
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="lastName">Last Name</Label>
                        <Controller
                            name="lastName"
                            control={control}
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
                                    id="lastName"
                                    placeholder="last name"
                                    type="text"
                                    disabled={isLoading}
                                />
                            )}
                        />
                        {errors.lastName && (
                            <span className="text-xs text-destructive">
                                {errors.lastName.message}
                            </span>
                        )}
                    </div>
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
                                pattern: {
                                    value: REGEX.PASSWORD_POLICY,
                                    message: ERROR_MESSAGE.INVALID_PASSWORD,
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
                    <div className="grid gap-2">
                        <Label htmlFor="password">Confirm Password</Label>
                        <Controller
                            name="confirmPassword"
                            control={control}
                            defaultValue=""
                            rules={{
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                                validate: (confirmPassword, allValues) => {
                                    if (
                                        confirmPassword !== allValues.password
                                    ) {
                                        return "Passwords do not match";
                                    }
                                    return true;
                                },
                            }}
                            render={({ field }) => (
                                <Input
                                    {...field}
                                    id="confirmPassword"
                                    placeholder="confirm password"
                                    type="password"
                                    disabled={isLoading}
                                />
                            )}
                        />
                        {errors.confirmPassword && (
                            <span className="text-xs text-destructive">
                                {errors.confirmPassword.message}
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
