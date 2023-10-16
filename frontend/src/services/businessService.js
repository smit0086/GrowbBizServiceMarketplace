export const createBusiness = async (
    businessName,
    email,
    categoryId,
    role,
    verificationDocuments
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
            await fetch(`/api/business/save`, {
                method: "POST",
                body: formData,
            })
        ).json();
        if (resp.businesses.length) {
            window.location.reload();
        }
    } catch (err) {
        console.error(err);
    }
};
