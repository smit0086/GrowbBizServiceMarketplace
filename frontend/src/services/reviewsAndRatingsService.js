export const getReviewsAndRatingsByServiceId = async (token, serviceId) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/reviews/allReviewsAndRatingsByServiceId?serviceID=${serviceId}`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.reviewsAndRatings;
    } catch (err) {
        console.error({ err });
        return [];
    }
};