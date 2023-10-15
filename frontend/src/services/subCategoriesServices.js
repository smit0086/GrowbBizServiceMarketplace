export const addSubCategory = async (subcategory_name, tax) => {
    const body = {
        subcategory_name,
        tax
    };
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/admin/addSubCategory`,
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

export const updateSubCategory = async (subcategory_id, subcategory_name, tax) => {
    const body = {
        subcategory_id,
        subcategory_name,
        tax
    };
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/admin/updateSubCategory`,
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

export const deleteSubCategory = async (subcategory_id, subcategory_name, tax) => {
    const body = {
        subcategory_id,
        subcategory_name,
        tax
    };
    const resp = await fetch(
        `${process.env.SERVER_ADDRESS}/admin/deleteSubCategory`,
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







