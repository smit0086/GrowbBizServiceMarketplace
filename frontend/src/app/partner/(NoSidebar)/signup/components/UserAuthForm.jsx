"use client";

import * as React from "react";
import { signIn } from "next-auth/react";
import { useForm } from "react-hook-form";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ERROR_MESSAGE, REGEX, ROLES } from "@/lib/constants";
import { signup } from "@/services/authService";

export function UserAuthForm({ className, ...props }) {
    const {
        register,
        handleSubmit,
        formState: { errors },
        watch,
    } = useForm();
    const onSubmit = async (data) => {
        setIsLoading(true);
        const res = await signup(
            data.firstName,
            data.lastName,
            data.email,
            data.password,
            ROLES.PARTNER
        );
        if (res) {
            await signIn("credentials", {
                email: data.email,
                password: data.password,
                role: ROLES.PARTNER,
                redirect: true,
                callbackUrl: props.callbackUrl ?? "/",
            });
        }
        setIsLoading(false);
    };
    const [isLoading, setIsLoading] = React.useState(false);

    return (
        <div className={cn("grid gap-6", className)} {...props}>
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="grid gap-2">
                    <div className="grid gap-2">
                        <Label htmlFor="email">First name</Label>
                        <Input
                            id="firstName"
                            placeholder="first name"
                            type="text"
                            autoCapitalize="none"
                            autoCorrect="off"
                            disabled={isLoading}
                            {...register("firstName", {
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                            })}
                        />
                        <span className="text-xs text-destructive">
                            {errors.firstName?.message}
                        </span>
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="lastName">Last name</Label>
                        <Input
                            id="lastName"
                            placeholder="last name"
                            type="text"
                            autoCapitalize="none"
                            autoCorrect="off"
                            disabled={isLoading}
                            {...register("lastName", {
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                            })}
                        />
                        <span className="text-xs text-destructive">
                            {errors.lastName?.message}
                        </span>
                    </div>
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
                    <div className="grid gap-2">
                        <Label htmlFor="confirm_password">
                            Confirm password
                        </Label>
                        <Input
                            id="confirm_password"
                            type="password"
                            placeholder="confrm password"
                            disabled={isLoading}
                            {...register("confirm_password", {
                                required: {
                                    value: true,
                                    message: ERROR_MESSAGE.REQUIRED,
                                },
                                validate: (val) => {
                                    if (watch("password") != val) {
                                        return ERROR_MESSAGE.PASSWORD_NOT_MATCH;
                                    }
                                },
                            })}
                        />
                        <span className="text-xs text-destructive">
                            {errors.confirm_password?.message}
                        </span>
                    </div>
                    <Button disabled={isLoading} type="submit">
                        {isLoading && (
                            <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                        )}
                        Register
                    </Button>
                </div>
            </form>
        </div>
    );
}
