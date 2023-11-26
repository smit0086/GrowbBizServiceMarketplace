import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import Rating from "@/components/modules/Rating/Rating";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { getReviewsAndRatingsByServiceId } from "@/services/reviewsAndRatingsService";
import { getUserDetails } from "@/services/userService";
import ReviewForm from "./ReviewForm";

const getReview = async (review) => {
    const session = await getServerSession(authOptions);
    const userDetail = await getUserDetails(review.userEmail, session.apiToken);
    return {
        ...review,
        reviewer: userDetail?.firstName + " " + userDetail?.lastName,
        isCurrentUser: session.user.email === review.userEmail,
    };
};
const getReviews = async (reviews) => {
    const formattedReviews = [];
    let currentUserReview = null;
    let hasCurrentUserReview = false;
    for (const review of reviews) {
        const formattedReview = await getReview(review);
        if (formattedReview.isCurrentUser) {
            hasCurrentUserReview = true;
            currentUserReview = formattedReview;
        } else {
            formattedReviews.push(await getReview(review));
        }
    }
    if (currentUserReview) {
        formattedReviews.unshift(currentUserReview);
    }
    return { formattedReviews, hasCurrentUserReview };
};

const Review = ({ review, serviceId }) => {
    return (
        <Card className="my-4">
            <CardHeader>
                <div className="flex justify-between text-lg font-semibold tracking-tight">
                    <span>
                        {review.reviewer} {review.isCurrentUser && "(You)"}
                    </span>
                    {review.isCurrentUser && (
                        <ReviewForm
                            review={review}
                            isEdit={true}
                            serviceId={serviceId}
                        />
                    )}
                </div>
                <Rating rating={review.rating} readOnly={true} />
            </CardHeader>

            <CardContent className="text-lg -mt-5">{review.review}</CardContent>
        </Card>
    );
};

const ReviewsSection = async ({ serviceId }) => {
    const session = await getServerSession(authOptions);
    const reviews = await getReviewsAndRatingsByServiceId(
        session.apiToken,
        serviceId
    );
    const { formattedReviews, hasCurrentUserReview } = await getReviews(
        reviews
    );
    return (
        <>
            <div className="flex justify-between">
                <h3 className="text-4xl font-semibold tracking-tight">
                    Reviews
                </h3>
                {!hasCurrentUserReview && (
                    <ReviewForm
                        isEdit={false}
                        review={null}
                        serviceId={serviceId}
                    />
                )}
            </div>
            <div className="my-8">
                {formattedReviews.map((review) => (
                    <Review
                        key={review.reviewAndRatingID}
                        review={review}
                        serviceId={serviceId}
                    />
                ))}
                {formattedReviews.length === 0 && (
                    <div className="text-xl">No review available</div>
                )}
            </div>
        </>
    );
};

export default ReviewsSection;
