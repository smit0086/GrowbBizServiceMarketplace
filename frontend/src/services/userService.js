export const getUserDetails = async (email, token) => {
    try {
        const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/user?email=${email}&role=CUSTOMER`;
        const resp = await fetch(endpoint, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        if (resp.ok) {
            const data = await resp.json();
            return data;
        }
        throw resp;
    } catch (err) {
        console.error(err);
        return null;
    }
};
