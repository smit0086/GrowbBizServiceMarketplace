"use client";

import * as React from "react";
import { useState } from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import {
    Dialog,
    DialogContent,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ERROR_MESSAGE } from "@/lib/constants";
import { useForm, Controller } from "react-hook-form";
import {
    verifyBusiness,
    downloadDocumentByEmail,
} from "@/services/businessService";
import { BUSSINESS_STATUS } from "@/lib/constants";
import { Icons } from "@/components/icons";

const BusinessApprovalForm = ({
    business,
    authSession,
    setRefreshBusinesses,
    categories,
}) => {
    const {
        handleSubmit,
        control,
        formState: { errors },
    } = useForm();
    const [isLoading, setIsLoading] = useState(false);
    const [isApprovalDialogOpen, setApprovalDialogOpen] = useState(false);
    const [isRejectionDialogOpen, setRejectionDialogOpen] = useState(false);

    const handleApproveBusiness = async (data) => {
        setIsLoading(true);
        const verification_response = await verifyBusiness(
            authSession.apiToken,
            data.business.businessId,
            BUSSINESS_STATUS.APPROVED,
            ""
        );
        setIsLoading(false);
        setApprovalDialogOpen(false);
        setRefreshBusinesses(true);
    };

    const handleRejectBusiness = async (data) => {
        setIsLoading(true);
        const verification_response = await verifyBusiness(
            authSession.apiToken,
            data.business.businessId,
            BUSSINESS_STATUS.DECLINED,
            data.formData.rejection_reason
        );
        setIsLoading(false);
        setRejectionDialogOpen(false);
        setRefreshBusinesses(true);
    };

    const downloadDocument = async () => {
        const response = await downloadDocumentByEmail(
            authSession.apiToken,
            business.email
        );

        const contentDisposition = response.headers.get("content-disposition");
        const filenameMatch =
            contentDisposition && contentDisposition.match(/filename="(.+)"/);
        const filename = filenameMatch
            ? filenameMatch[1]
            : `Verification_Document_of_${business.businessName}.jpg`;
        const imageBlob = await response.blob();
        const imageUrl = URL.createObjectURL(imageBlob);
        const downloadLink = document.createElement("a");
        downloadLink.href = imageUrl;
        downloadLink.download = filename;
        downloadLink.click();
    };

    return (
        <>
            <Card key={business.businessId} className="w-[350px]">
                <CardHeader>
                    <CardTitle>{business.businessName}</CardTitle>
                    <CardDescription>Business details</CardDescription>
                </CardHeader>
                <CardContent>
                    <div className="grid w-full items-center gap-4">
                        <div className="flex flex-col space-y-1.5">
                            <Label
                                htmlFor={`category-${business.businessId}`}
                                style={{ fontSize: "1rem", fontWeight: "bold" }}
                            >
                                Category
                            </Label>
                            <span
                                id={`category-${business.businessId}`}
                                style={{ fontSize: "0.875rem" }}
                            >
                                {
                                    categories.find(
                                        (category) =>
                                            category.categoryID ===
                                            business.categoryId
                                    )?.name
                                }
                            </span>
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label
                                htmlFor={`document-${business.businessId}`}
                                style={{ fontSize: "1rem", fontWeight: "bold" }}
                            >
                                Documents
                            </Label>
                            <span id={`document-${business.businessId}`}>
                                <Link
                                    href={business.fileURL}
                                    onClick={downloadDocument}
                                    className="underline underline-offset-4 hover:text-primary"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    style={{ fontSize: "0.875rem" }}
                                >
                                    Download
                                </Link>
                            </span>
                        </div>
                    </div>
                </CardContent>
                <CardFooter className="flex justify-between">
                    <div className="flex space-x-4">
                        <Dialog
                            open={isRejectionDialogOpen}
                            onOpenChange={setRejectionDialogOpen}
                        >
                            <DialogTrigger asChild>
                                <Button variant="destructive">Reject</Button>
                            </DialogTrigger>
                            <DialogContent className="sm:max-w-[425px]">
                                <DialogHeader>
                                    <DialogTitle>Confirmation</DialogTitle>
                                </DialogHeader>
                                <form
                                    onSubmit={handleSubmit((formData) => {
                                        handleRejectBusiness({
                                            business,
                                            formData,
                                        });
                                    })}
                                >
                                    <div style={{ marginBottom: "1.2rem" }}>
                                        <div>
                                            <Label
                                                htmlFor="name"
                                                className="text-right"
                                            >
                                                Please provide the reason to
                                                reject the{" "}
                                                {business.businessName}
                                            </Label>
                                            <Controller
                                                name="rejection_reason"
                                                control={control}
                                                defaultValue=""
                                                disabled={isLoading}
                                                rules={{
                                                    required: {
                                                        value: true,
                                                        message:
                                                            ERROR_MESSAGE.REQUIRED,
                                                    },
                                                }}
                                                render={({ field }) => (
                                                    <Input
                                                        {...field}
                                                        id="rejection_reason"
                                                        type="text"
                                                    />
                                                )}
                                            />
                                            {errors.rejection_reason && (
                                                <span className="text-xs text-destructive">
                                                    {
                                                        errors.rejection_reason
                                                            .message
                                                    }
                                                </span>
                                            )}
                                        </div>
                                    </div>
                                    <DialogFooter>
                                        <Button
                                            type="submit"
                                            disabled={isLoading}
                                        >
                                            {isLoading && (
                                                <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                            )}
                                            Confirm
                                        </Button>
                                    </DialogFooter>
                                </form>
                            </DialogContent>
                        </Dialog>
                        <Dialog
                            open={isApprovalDialogOpen}
                            onOpenChange={setApprovalDialogOpen}
                        >
                            <DialogTrigger asChild>
                                <Button>Approve</Button>
                            </DialogTrigger>
                            <DialogContent className="sm:max-w-[425px]">
                                <DialogHeader>
                                    <DialogTitle>Confirmation</DialogTitle>
                                </DialogHeader>
                                <form
                                    onSubmit={handleSubmit((formData) =>
                                        handleApproveBusiness({
                                            business,
                                            formData,
                                        })
                                    )}
                                >
                                    <div style={{ marginBottom: "1.2rem" }}>
                                        <div>
                                            <Label
                                                htmlFor="name"
                                                className="text-right"
                                            >
                                                Are you sure you want to approve
                                                the {business.businessName}?
                                            </Label>
                                        </div>
                                    </div>
                                    <DialogFooter>
                                        <Button
                                            type="submit"
                                            disabled={isLoading}
                                        >
                                            {isLoading && (
                                                <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                            )}
                                            Confirm
                                        </Button>
                                    </DialogFooter>
                                </form>
                            </DialogContent>
                        </Dialog>
                    </div>
                </CardFooter>
            </Card>
        </>
    );
};

export default BusinessApprovalForm;
