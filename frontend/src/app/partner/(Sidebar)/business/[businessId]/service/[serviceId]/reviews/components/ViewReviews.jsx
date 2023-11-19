"use client";

import * as React from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";

const ViewReviews = ({ reviews }) => {
    return (
        <>
            <h2 className="text-3xl p-8 pl-16">Reviews</h2>
            {reviews.length === 0 ?
                <div className="p-8 pl-16">
                    No Reviews.
                </div>
                :
                <div>
                    <div className="flex flex-wrap gap-4 p-8 pl-16">
                        {reviews.map((review) => (
                            <Card key={review.reviewAndRatingID} className="w-[350px]">
                                <CardContent>
                                    <div className="grid w-full items-center gap-4">
                                        <br />
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`review-${review.reviewAndRatingID}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Customer Email</Label>
                                            <span id={`review-${review.reviewAndRatingID}`} style={{ fontSize: '0.875rem' }}>{review.userEmail}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5 ">
                                            <Label htmlFor={`review-${review.reviewAndRatingID}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Rating</Label>
                                            <span id={`review-${review.reviewAndRatingID}`} style={{ fontSize: '0.875rem' }}>{review.rating}</span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label htmlFor={`review-${review.reviewAndRatingID}`} style={{ fontSize: '1rem', fontWeight: 'bold' }}>Description</Label>
                                            <span id={`review-${review.reviewAndRatingID}`} style={{ fontSize: '0.875rem' }}>{review.review}</span>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                </div>
            }
        </>
    )
}

export default ViewReviews;