"use client";

import * as React from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { MoveLeft } from "lucide-react";

const ViewReviews = ({ reviews, businessId }) => {
    return (
        <div className="px-16 py-8">
            <div>
                <a
                    className="text-xs"
                    href={`/partner/business/${businessId}/service`}
                >
                    <div className="flex items-center">
                        <MoveLeft size={20} />
                        <span className="ml-1 hover:underline">
                            Back to services
                        </span>
                    </div>
                </a>
                <h2 className="text-4xl font-semibold tracking-tight">
                    Reviews
                </h2>
            </div>
            {reviews.length === 0 ? (
                <div className="py-8">
                    Looks like this service does not have any reviews !
                </div>
            ) : (
                <div>
                    <div className="flex flex-wrap my-6 gap-4">
                        {reviews.map((review) => (
                            <Card
                                key={review.reviewAndRatingID}
                                className="w-[350px]"
                            >
                                <CardContent className="pt-6">
                                    <div className="grid w-full items-center gap-4">
                                        <div className="flex flex-col space-y-1.5">
                                            <Label
                                                htmlFor={`review-${review.reviewAndRatingID}`}
                                                style={{
                                                    fontSize: "1rem",
                                                    fontWeight: "bold",
                                                }}
                                            >
                                                Customer Email
                                            </Label>
                                            <span
                                                id={`review-${review.reviewAndRatingID}`}
                                                style={{ fontSize: "0.875rem" }}
                                            >
                                                {review.userEmail}
                                            </span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5 ">
                                            <Label
                                                htmlFor={`review-${review.reviewAndRatingID}`}
                                                style={{
                                                    fontSize: "1rem",
                                                    fontWeight: "bold",
                                                }}
                                            >
                                                Rating
                                            </Label>
                                            <span
                                                id={`review-${review.reviewAndRatingID}`}
                                                style={{ fontSize: "0.875rem" }}
                                            >
                                                {review.rating}
                                            </span>
                                        </div>
                                        <div className="flex flex-col space-y-1.5">
                                            <Label
                                                htmlFor={`review-${review.reviewAndRatingID}`}
                                                style={{
                                                    fontSize: "1rem",
                                                    fontWeight: "bold",
                                                }}
                                            >
                                                Description
                                            </Label>
                                            <span
                                                id={`review-${review.reviewAndRatingID}`}
                                                style={{ fontSize: "0.875rem" }}
                                            >
                                                {review.review}
                                            </span>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                </div>
            )}
        </div>
    );
};

export default ViewReviews;
