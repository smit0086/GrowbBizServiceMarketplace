export const sendEmailReminder = async (token, bookingId) => {
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/email/sendEmailReminder?bookingId=${bookingId}`,
        {
            method: "post",
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