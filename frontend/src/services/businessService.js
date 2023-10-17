export const getBusinessesByPendingStatus = async (token) => {
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/all?status=PENDING`,
        {
            method: "get",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
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

export const getBusinessByEmail = async (token, email) => {
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/business/?email=${email}`,
        {
            method: "get",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
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

export const verifyBusiness = async (token, businessId, status, reason) => {
    const requestBody = {
        status,
        reason
    };

    try {
        const resp = await fetch(
            `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/${businessId}/verify`,
            {
                method: "put",
                body: JSON.stringify(requestBody),
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                }
            }
        );
        
        if (!resp.ok) {
            throw new Error(`Request failed with status: ${resp.status}`);
        }

        return resp;
    } catch (err) {
        console.error("Error during the API request:", err);
    }
};

export const downloadDocumentByEmail = async (token, email) => {
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/download?email=${email}`,
        {
            method: "get",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
        }
    );
    try {
        return resp;
    } catch (err) {
        console.log("err", err);
    }
};