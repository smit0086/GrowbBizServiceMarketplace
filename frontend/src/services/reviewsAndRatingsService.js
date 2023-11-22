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

export const addReview = async (token, payload) => {
    try {
        const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/reviews/addReviewAndRating`;
        const re = await fetch(endpoint, {
            method: "post",
            headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(payload),
        });
        if (re.ok) {
            return true;
        }
        throw re;
    } catch (err) {
        console.error({ err });
        return false;
    }
};

export const deleteReview = async (token, reviewId) => {
    try {
        const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/reviews/deleteReviewAndRating?reviewAndRatingId=${reviewId}`;
        const re = await fetch(endpoint, {
            method: "delete",
            headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        if (re.ok) {
            return true;
        }
        throw re;
    } catch (err) {
        console.error({ err });
        return false;
    }
};

export const updateReview = async (token, payload) => {
    try {
        const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/reviews/updateReviewAndRating`;
        const re = await fetch(endpoint, {
            method: "post",
            headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(payload),
        });
        if (re.ok) {
            return true;
        }
        throw re;
    } catch (err) {
        console.error({ err });
        return false;
    }
};

export const getAllReviewsAndRatings = async (token) => {
    try {
        const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/reviews/allReviewsAndRatings`;
        const re = await fetch(endpoint, {
            method: "get",
            headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        if (re.ok) {
            return (await re.json()).reviewsAndRatings;
        }
    } catch (err) {
        console.error({ err });
        return [];
    }
};
