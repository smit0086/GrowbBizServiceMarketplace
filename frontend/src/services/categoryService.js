export const getAllCategories = async (token) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/category/allCategories`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.categories;
    } catch (err) {
        console.error({ err });
        return [];
    }
};
