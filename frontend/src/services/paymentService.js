export const createPaymentIntent = async (formData, authtoken, abortSignal) => {
    const { date, serviceId, startTime, endTime, note } = formData;
    try {
        const responseObject = await fetch(
            `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/payment/init`,
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${authtoken}`,
                },
                body: JSON.stringify({
                    date,
                    serviceId,
                    startTime,
                    endTime,
                    note,
                }),
                signal: abortSignal,
            }
        );
        if (responseObject.ok) {
            const response = await responseObject.json();
            console.log({ response });
            return response;
        }
        throw responseObject;
    } catch (error) {
        console.error({ error });
        return null;
    }
};

export const getPaymentDetail = async (authtoken, paymentId) => {
    const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/payment?paymentId=${paymentId}`;
    try {
        const responseObject = await fetch(endpoint, {
            headers: {
                Authorization: `Bearer ${authtoken}`,
            },
        });
        if (responseObject.ok) {
            const response = await responseObject.json();
            return response;
        }
        throw responseObject;
    } catch (err) {
        console.error(err);
        return null;
    }
};

export const getAllPayments = async (authtoken) => {
    const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/payment/all`;
    try {
        const responseObject = await fetch(endpoint, {
            headers: {
                Authorization: `Bearer ${authtoken}`,
            },
        });
        if (responseObject.ok) {
            const response = await responseObject.json();
            return response;
        }
        throw responseObject;
    } catch (err) {
        console.error(err);
        return [];
    }
};
