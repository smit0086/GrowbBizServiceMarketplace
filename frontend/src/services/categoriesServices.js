export const addCategory = async (token, category_name, tax) => {
    const body = {
        name: category_name,
        tax,
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/addCategory`,
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

export const updateCategory = async (token, category_id, category_name, tax) => {
    const body = {
        id: category_id,
        name: category_name,
        tax,
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/updateCategory`,
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

export const deleteCategory = async (category_id, category_name, tax) => {
    const body = {
        category_id,
        category_name,
        tax,
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/deleteCategory`,
        {
            method: "post",
            body: JSON.stringify(body),
            headers: {
                "Content-Type": "application/json",
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

export const getAllCategories = async (token) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/allCategories`,
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
