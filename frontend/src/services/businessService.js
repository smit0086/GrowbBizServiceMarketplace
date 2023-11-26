export const createBusiness = async (
    businessName,
    email,
    categoryId,
    role,
    verificationDocuments,
    token
) => {
    const businessData = {
        businessName: businessName,
        email: email,
        categoryId: categoryId,
        role: role,
    };
    const formData = new FormData();
    formData.append("file", verificationDocuments);
    formData.append("business", JSON.stringify(businessData));
    try {
        const resp = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/save`,
                {
                    method: "POST",
                    body: formData,
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return resp;
    } catch (err) {
        console.error(err);
    }
};

export const getBusiness = async (email, token) => {
    const resp = await (
        await fetch(
            `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/?email=${email}`,
            {
                method: "get",
                headers: {
                    "Content-type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            }
        )
    ).json();
    return resp;
};

export const updateBusiness = async (
    businessName,
    email,
    categoryId,
    role,
    verificationDocuments,
    token,
    businessId
) => {
    const businessData = {
        businessName: businessName,
        email: email,
        categoryId: categoryId,
        role: role,
    };
    const formData = new FormData();
    formData.append("file", verificationDocuments);
    formData.append("business", JSON.stringify(businessData));
    try {
        const resp = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/update/${businessId}`,
                {
                    method: "PUT",
                    body: formData,
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return resp;
    } catch (err) {
        console.error(err);
    }
};

export const getBusinessesByPendingStatus = async (token) => {
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/all?status=PENDING`,
        {
            method: "get",
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

export const getBusinessByEmail = async (token, email) => {
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/business/?email=${email}`,
        {
            method: "get",
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

export const verifyBusiness = async (token, businessId, status, reason) => {
    const requestBody = {
        status,
        reason,
    };

    try {
        const resp = await fetch(
            `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/${businessId}/verify`,
            {
                method: "put",
                body: JSON.stringify(requestBody),
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
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
                Authorization: `Bearer ${token}`,
            },
        }
    );
    try {
        return resp;
    } catch (err) {
        console.log("err", err);
    }
};

export const getBusinessHours = async (token, businessId) => {
    const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/businessHours?businessId=${businessId}`;
    const resp = await fetch(endpoint, {
        method: "get",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
    });
    try {
        if (resp.ok) {
            return await resp.json();
        }
        throw resp;
    } catch (err) {
        console.log("err", err);
    }
};

export const saveBusinessHours = async (token, businessId, businessHours) => {
    const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/businessHours`;
    const resp = await fetch(endpoint, {
        method: "put",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
            businessId: parseInt(businessId),
            businessHours,
        }),
    });
    try {
        if (resp.ok) {
            return true;
        }
        throw resp;
    } catch (err) {
        console.error("err", err);
        return false;
    }
};

export const getAllBusinesses = async (token) => {
    try {
        const endpoint = `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/business/all`;
        const resp = await fetch(endpoint, {
            method: "get",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        });
        if (resp.ok) {
            return (await resp.json()).businesses;
        }
        throw resp;
    } catch (err) {
        console.error("err", err);
        return [];
    }
};
