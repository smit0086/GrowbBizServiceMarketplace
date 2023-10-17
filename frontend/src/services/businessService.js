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
            await fetch(`${NEXT_PUBLIC_SERVER_ADDRESS}/api/business/save`, {
                method: "POST",
                body: formData,
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
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
