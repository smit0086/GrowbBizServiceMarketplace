"use client";

import React, { useState, useEffect } from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import Link from "next/link";
import {
    Dialog,
    DialogContent,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { useForm, Controller } from 'react-hook-form';
import { ERROR_MESSAGE } from "@/lib/constants";

export default function BusinessApproval() {
    const [businesses, setBusinesses] = useState([]);
    const { handleSubmit, control, formState: { errors } } = useForm();

    useEffect(() => {
        const initialBusinesses = [
            {
                id: 1,
                name: "Business 1",
                category: "Category 1",
                documentUrl: "https://example.com/document1.pdf",
            },
            {
                id: 2,
                name: "Business 2",
                category: "Category 2",
                documentUrl: "https://example.com/document2.pdf",
            },
            {
                id: 3,
                name: "Business 3",
                category: "Category 3",
                documentUrl: "https://example.com/document3.pdf",
            },
        ];

        setBusinesses(initialBusinesses);
    }, []);

    const handleAcceptBusiness = (data) => {
        console.log("approved");
        console.log(data.business);
    }

    const handleRejectBusiness = (data) => {
        console.log("rejected");
        console.log(data.business);
        console.log(data.formData.rejection_reason);
    }

    return (
        <>
            <div className="flex flex-wrap gap-4" style={{ marginTop: '1%', marginLeft: '1%' }}>
                {businesses.map((business) => (
                    <Card key={business.id} className="w-[350px]">
                        <CardHeader>
                            <CardTitle>{business.name}</CardTitle>
                            <CardDescription>Business details</CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="grid w-full items-center gap-4">
                                <div className="flex flex-col space-y-1.5">
                                    <Label htmlFor={`category-${business.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Category</Label>
                                    <span id={`category-${business.id}`} style={{ fontSize: '0.875rem' }}>{business.category}</span>
                                </div>
                                <div className="flex flex-col space-y-1.5">
                                    <Label htmlFor={`document-${business.id}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Documents</Label>
                                    <span id={`document-${business.id}`}>
                                        <Link href={business.documentUrl} className="underline underline-offset-4 hover:text-primary" target="_blank" rel="noopener noreferrer" style={{ fontSize: '0.875rem' }}>
                                            Download
                                        </Link>
                                    </span>
                                </div>
                            </div>
                        </CardContent>
                        <CardFooter className="flex justify-between">
                            <div className="flex space-x-4">
                                <Dialog>
                                    <DialogTrigger asChild>
                                        <Button variant="destructive">Reject</Button>
                                    </DialogTrigger>
                                    <DialogContent className="sm:max-w-[425px]">
                                        <DialogHeader>
                                            <DialogTitle>Confirmation</DialogTitle>
                                        </DialogHeader>
                                        <form onSubmit={handleSubmit((formData) => {
                                            handleRejectBusiness({ business, formData });
                                        })}>
                                            <div style={{ marginBottom: '1.2rem' }}>
                                                <div>
                                                    <Label htmlFor="name" className="text-right">
                                                        Please provide the reason to reject the {business.name}
                                                    </Label>
                                                    <Controller
                                                        name="rejection_reason"
                                                        control={control}
                                                        defaultValue=""
                                                        rules={{
                                                            required: {
                                                                value: true,
                                                                message: ERROR_MESSAGE.REQUIRED
                                                            }
                                                        }}
                                                        render={({ field }) => (
                                                            <Input
                                                                {...field}
                                                                id="rejection_reason"
                                                                type="text"
                                                            />
                                                        )}
                                                    />
                                                    {errors.rejection_reason && <span className="text-xs text-destructive">{errors.rejection_reason.message}</span>}
                                                </div>
                                            </div>
                                            <DialogFooter>
                                                <Button type="submit">Confirm</Button>
                                            </DialogFooter>
                                        </form>
                                    </DialogContent>
                                </Dialog>
                                <Dialog>
                                    <DialogTrigger asChild>
                                        <Button>Accept</Button>
                                    </DialogTrigger>
                                    <DialogContent className="sm:max-w-[425px]">
                                        <DialogHeader>
                                            <DialogTitle>Confirmation</DialogTitle>
                                        </DialogHeader>
                                        <form onSubmit={handleSubmit((formData) => handleAcceptBusiness({ business, formData }))}>
                                            <div style={{ marginBottom: '1.2rem' }}>
                                                <div>
                                                    <Label htmlFor="name" className="text-right">
                                                        Are you sure you want to approve the {business.name}?
                                                    </Label>
                                                </div>
                                            </div>
                                            <DialogFooter>
                                                <Button type="submit">Confirm</Button>
                                            </DialogFooter>
                                        </form>
                                    </DialogContent>
                                </Dialog>
                            </div>
                        </CardFooter>
                    </Card>
                ))}
            </div>
        </>
    );
}
