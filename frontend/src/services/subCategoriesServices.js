export const addSubCategory = async (token, category_name, tax) => {
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

export const updateCategory = async (
    token,
    category_id,
    category_name,
    tax
) => {
    const body = {
        categoryID: category_id,
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

export const deleteCategory = async (
    token,
    category_id,
    category_name,
    tax
) => {
    const body = {
        categoryID: category_id,
        name: category_name,
        tax,
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/deleteCategory`,
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

export const getAllSubCategories = async (token) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/category/allSubCategories`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.subCategories;
    } catch (err) {
        console.error({ err });
        return [];
    }
};

export const getAllSubCategoriesForCategoryId = async (token, categoryId) => {
    try {
        const re = await fetch(
            `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/category/getAllSubCategoriesForCategoryId?categoryId=${categoryId}`,
            {
                method: "get",
                headers: {
                    "Content-type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        if (re.ok) {
            const resp = await re.json();
            return resp.subCategories;
        }
        throw re;
    } catch (err) {
        console.error({ err });
        return [];
    }
};
