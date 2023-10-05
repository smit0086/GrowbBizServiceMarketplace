"use client";

import * as React from "react";

import { cn } from "@/lib/utils";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useForm, Controller } from 'react-hook-form';

export function UserAuthForm({ className, ...props }) {
    const [isLoading, setIsLoading] = React.useState(false);
    const { handleSubmit, control, formState: { errors } } = useForm({
        mode: "onChange"
    });

    async function onSubmit(data) {
        setIsLoading(true);
        
        console.log(data);

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
                                required: 'First Name is required'
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
                        {errors.firstName && <p className="text-sm text-red-500">{errors.firstName.message}</p>}
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="lastName">Last Name</Label>
                        <Controller
                            name="lastName"
                            control={control}
                            defaultValue=""
                            rules={{
                                required: 'Last Name is required'
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
                        {errors.lastName && <p className="text-sm text-red-500">{errors.lastName.message}</p>}
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="email">Email</Label>
                        <Controller
                            name="email"
                            control={control}
                            defaultValue=""
                            rules={{
                                required: 'Email is required',
                                pattern: {
                                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                    message: 'Invalid email address',
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
                        {errors.email && <p className="text-sm text-red-500">{errors.email.message}</p>}
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="password">Password</Label>
                        <Controller
                            control={control}
                            name="password"
                            defaultValue=""
                            rules={{
                                required: 'Password is required',
                                minLength: {
                                    value: 8,
                                    message: 'Password must be at least 8 characters long',
                                  },
                                  pattern: {
                                    value: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/,
                                    message: 'Password must contain at least one letter, one number, and one special symbol',
                                  }
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
                        {errors.password && <p className="text-sm text-red-500">{errors.password.message}</p>}
                    </div>
                    <div className="grid gap-2">
                        <Label htmlFor="password">Confirm Password</Label>
                        <Controller
                            name="confirmPassword"
                            control={control}
                            defaultValue=""
                            rules={{
                                required: 'Confirm Password is required',
                                validate: (confirmPassword, allValues) => {
                                    if (confirmPassword !== allValues.password) {
                                        return 'Passwords do not match';
                                    }
                                    return true;
                                }
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
                        {errors.confirmPassword && <p className="text-sm text-red-500">{errors.confirmPassword.message}</p>}
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