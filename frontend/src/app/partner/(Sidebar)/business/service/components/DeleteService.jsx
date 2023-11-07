"use client";

import * as React from "react";
import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog";
import { useForm } from 'react-hook-form';
import { Label } from "@/components/ui/label";
import { deleteService } from "@/services/servicesService";

const DeleteService = ({ authSession, service, services, setServices }) => {
    const { handleSubmit } = useForm();
    const [isLoading, setIsLoading] = useState(false);
    const [isDialogOpen, setDialogOpen] = useState(false);

    const handleDeleteService = async (data) => {
        setIsLoading(true);
        const deletedService = await deleteService(authSession.apiToken, service.serviceId);
        const updatedServices = services.filter((s) => s.serviceId !== data.service.serviceId);
        setServices(updatedServices);
        setIsLoading(false);
        setDialogOpen(false);
    }

    return (
        <>
            <Dialog open={isDialogOpen} onOpenChange={setDialogOpen}>
                <DialogTrigger asChild>
                    <Button variant="destructive">Delete</Button>
                </DialogTrigger>
                <DialogContent className="sm:max-w-[425px]">
                    <DialogHeader>
                        <DialogTitle>Confirmation</DialogTitle>
                    </DialogHeader>
                    <form onSubmit={handleSubmit((formData) => handleDeleteService({ service, formData }))}>
                        <div style={{ marginBottom: '1.2rem' }}>
                            <div>
                                <Label htmlFor="name" className="text-right">
                                    Are you sure you want to delete the {service.serviceName}?
                                </Label>
                            </div>
                        </div>
                        <DialogFooter>
                            <Button type="submit" disabled={isLoading}>
                                {/* {isLoading && (
                                    <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                )} */}
                                Confirm
                            </Button>
                        </DialogFooter>
                    </form>
                </DialogContent>
            </Dialog>
        </>
    );
}

export default DeleteService;