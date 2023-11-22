"use client";
import React from "react";
import { useSession } from "next-auth/react";
import { Pencil, Plus, Trash } from "lucide-react";
import Rating from "@/components/modules/Rating/Rating";
import TooltipWrapper from "@/components/modules/TooltipWrapper/TooltipWrapper";
import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
} from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import {
    addReview,
    deleteReview,
    updateReview,
} from "@/services/reviewsAndRatingsService";

const FormDialog = ({ review, open, setOpen, isEdit, serviceId, token }) => {
    console.log({ serviceId });
    const [starRating, setStarRating] = React.useState(() =>
        review ? review.rating : 5
    );
    const [comment, setComment] = React.useState(() =>
        review ? review.review : ""
    );
    const onRatingChange = (rating) => {
        setStarRating(rating);
    };
    const onSave = async () => {
        const payload = {
            review: comment,
            rating: starRating,
            serviceId,
            ...(isEdit && { reviewAndRatingId: review.reviewAndRatingID }),
        };
        const mutateFn = isEdit ? updateReview : addReview;
        const resp = await mutateFn(token, payload);
        if (resp) {
            window.location.reload();
        }
    };
    return (
        <Dialog open={open} onOpenChange={setOpen}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle className="text-xl font-semibold tracking-tight">
                        Add your review
                    </DialogTitle>
                </DialogHeader>
                <div>
                    <Label>Rate your experience</Label>
                    <Rating rating={starRating} onChange={onRatingChange} />
                </div>
                <div>
                    <Label>Comment</Label>
                    <Textarea
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                    />
                </div>
                <Button disabled={!comment} onClick={onSave}>
                    Save
                </Button>
            </DialogContent>
        </Dialog>
    );
};

const ReviewForm = ({ review, isEdit, serviceId }) => {
    const [open, setOpen] = React.useState(false);
    const session = useSession();
    const token = session.data?.apiToken;
    const onDeleteReview = async () => {
        const resp = await deleteReview(token, review.reviewAndRatingID);
        if (resp) {
            window.location.reload();
        }
    };
    return (
        <>
            {isEdit && (
                <div className="flex">
                    <TooltipWrapper content={"Edit review"}>
                        <Pencil
                            key={isEdit}
                            className="cursor-pointer mr-2"
                            onClick={() => setOpen(true)}
                        />
                    </TooltipWrapper>
                    <AlertDialog>
                        <TooltipWrapper content={"Delete review"}>
                            <AlertDialogTrigger asChild>
                                <Trash className="cursor-pointer" />
                            </AlertDialogTrigger>
                        </TooltipWrapper>
                        <AlertDialogContent>
                            <AlertDialogHeader>
                                <AlertDialogTitle>
                                    Are you absolutely sure?
                                </AlertDialogTitle>
                                <AlertDialogDescription>
                                    This action cannot be undone. This will
                                    permanently delete your review.
                                </AlertDialogDescription>
                            </AlertDialogHeader>
                            <AlertDialogFooter>
                                <AlertDialogCancel>Cancel</AlertDialogCancel>
                                <AlertDialogAction onClick={onDeleteReview}>
                                    Continue
                                </AlertDialogAction>
                            </AlertDialogFooter>
                        </AlertDialogContent>
                    </AlertDialog>
                </div>
            )}
            {!isEdit && (
                <TooltipWrapper content={"Add review"}>
                    <Plus
                        key={isEdit}
                        size={36}
                        className="cursor-pointer"
                        onClick={() => setOpen(true)}
                    />
                </TooltipWrapper>
            )}
            {open && (
                <FormDialog
                    open={open}
                    setOpen={setOpen}
                    review={review}
                    isEdit={isEdit}
                    serviceId={serviceId}
                    token={token}
                />
            )}
        </>
    );
};

export default ReviewForm;
