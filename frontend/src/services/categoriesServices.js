export const addCategory = async (category_name, tax) => {
    const body = {
        category_name,
        tax
    };
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/admin/addCategory`,
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

export const updateCategory = async (category_id, category_name, tax) => {
    const body = {
        category_id,
        category_name,
        tax
    };
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/admin/updateCategory`,
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

export const deleteCategory = async (category_id, category_name, tax) => {
    const body = {
        category_id,
        category_name,
        tax
    };
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/admin/deleteCategory`,
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







