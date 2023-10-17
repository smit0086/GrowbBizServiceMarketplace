export const getAllCategories = async (token) => {
    try {
        const re = await (
            await fetch(`${process.env.SERVER_ADDRESS}/admin/allCategories`, {
                method: "get",
                headers: {
                    "Content-type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            })
        ).json();
        return re.categories;
    } catch (err) {
        console.err({ err });
        return [];
    }
};
