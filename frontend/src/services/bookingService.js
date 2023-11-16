export const getFreeTimeSlots = async (token, businessID, serviceID, date) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/booking/getSlot/${businessID}/${serviceID}?date=${date}`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.freeSlots;
    } catch (err) {
        console.error({ err });
        return [];
    }
};

export const bookService = async (token, serviceId, date, startTime, endTime, amount, note, email) => {
    const body = {
        serviceId,
        date,
        startTime,
        endTime,
        amount,
        note,
        email,
        role: "CUSTOMER"
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/booking/add`,
        {
            method: "post",
            body: JSON.stringify(body),
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        }
    );
    try {
        const data = await resp.json();
        return data;
    } catch (err) {
        console.log("err", err);
    }
};

export const getUpcomingBookingsForBusiness = async (token, businessId) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/booking/business/upcoming/${businessId}`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.bookings;
    } catch (err) {
        console.error({ err });
        return [];
    }
};

export const updateBookingStatus = async (token, bookingId, status) => {
    const body = {
        status
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/booking/${bookingId}/status/`,
        {
            method: "put",
            body: JSON.stringify(body),
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        }
    );
    try {
        const data = await resp.json();
        return data;
    } catch (err) {
        console.log("err", err);
    }
};

export const getOngoingBookingsForBusiness = async (token, businessId) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/booking/business/ongoing/${businessId}`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.bookings;
    } catch (err) {
        console.error({ err });
        return [];
    }
};