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
import { deleteSubCategory } from "@/services/subCategoriesServices";
import { useRouter } from "next/navigation";

const DeleteSubCategory = ({ subcategory }) => {
    const session = useSession();
    const { handleSubmit } = useForm();
    const [isLoading, setIsLoading] = useState(false);
    const [isDialogOpen, setDialogOpen] = useState(false);
    const router = useRouter();

    const handleDeleteSubCategory = async () => {
        setIsLoading(true);
        const deleteSubCategories = await deleteSubCategory(session.data.apiToken, subcategory.subCategoryID, subcategory.name, subcategory.categoryID)
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
                    <form onSubmit={handleSubmit((formData) => handleDeleteSubCategory())}>
                        <div style={{ marginBottom: '1.2rem' }}>
                            <div>
                                <Label htmlFor="name" className="text-right">
                                    Are you sure you want to delete the {subcategory.name}?
                                </Label>
                            </div>
                        </div>
                        <DialogFooter>
                            <Button type="submit" disabled={isLoading}>
                               Confirm
                            </Button>
                        </DialogFooter>
                    </form>
                </DialogContent>
            </Dialog>
        </>
    );
}

export default DeleteSubCategory;
