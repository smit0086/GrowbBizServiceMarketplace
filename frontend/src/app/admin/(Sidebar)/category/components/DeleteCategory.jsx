"use client";

import * as React from "react";
import { useEffect, useState} from "react"; 
import { useSession } from "next-auth/react";
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
import { deleteCategory } from "@/services/categoriesServices";
import { useRouter } from "next/navigation";

const DeleteCategory = ({ category }) => {
    const session = useSession();
    const { handleSubmit } = useForm();
    const [isLoading, setIsLoading] = useState(false);
    const [isDialogOpen, setDialogOpen] = useState(false);
    const router = useRouter();

    const handleDeleteCategory = async () => {
        setIsLoading(true);
        const deleteCategories = await deleteCategory(session.data.apiToken, category.categoryID, category.name, category.tax)
        setIsLoading(false);
        setDialogOpen(false);
        router.refresh()
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
                    <form onSubmit={handleSubmit((formData) => handleDeleteCategory())}>
                        <div style={{ marginBottom: '1.2rem' }}>
                            <div>
                                <Label htmlFor="name" className="text-right">
                                    Are you sure you want to delete the {category.name}?
                                </Label>
                            </div>
                        </div>
                        <DialogFooter>
                            <Button type="submit" disabled={isLoading}>
                                {/* {isLoading && (
                                    // <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
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

export default DeleteCategory;
