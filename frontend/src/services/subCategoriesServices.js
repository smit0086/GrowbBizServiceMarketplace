export const addSubCategory = async (token, subcategory_name, category_id) => {
    const body = {
        name: subcategory_name,
        categoryID: parseInt(category_id),
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/addSubCategory`,
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

export const updateSubCategory = async (
    token,
    subcategory_id,
    subcategory_name,
    category_id
) => {
    const body = {
        subCategoryID: subcategory_id,
        name: subcategory_name,
        categoryID: parseInt(category_id),
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/updateSubCategory`,
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

export const deleteSubCategory = async (
    token,
    subcategory_id,
    subcategory_name,
    category_id
) => {
    const body = {
        subCategoryID: parseInt(subcategory_id),
        name: subcategory_name,
        categoryID: parseInt(category_id),
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/admin/deleteSubCategory`,
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

export const getAllSubCategoriesForCategoryId = async (token, categoryId) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/category/getAllSubCategoriesForCategoryId?categoryId=${categoryId}`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re;
    } catch (err) {
        console.error({ err });
        return [];
    }
};

export const getAllSubCategoriesForCategory = async (token, categoryId) => {
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
            const data = await re.json();
            return data.subCategories;
        }
        throw re;
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
