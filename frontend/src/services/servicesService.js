export const addService = async (token, serviceName, description, timeRequired, businessID, subCategoryID, price, serviceImage) => {
    const body = {
        serviceName,
        description,
        price,
        timeRequired,
        businessID,
        subCategoryID,
        image: serviceImage
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/addService`,
        {
            method: "post",
            body: JSON.stringify(body),
            headers: {
                "Content-type": "application/json",
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

export const getServicesByBusinessId = async (token, businessID) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/allServicesByBusinessId?businessID=${businessID}`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.services;
    } catch (err) {
        console.error({ err });
        return [];
    }
};

export const updateService = async (token, serviceId, serviceName, description, timeRequired, businessID, subCategoryID, price, serviceImage) => {
    const body = {
        serviceID: serviceId,
        serviceName,
        description,
        price,
        timeRequired,
        businessID,
        subCategoryID,
        image: serviceImage
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/updateService`,
        {
            method: "put",
            body: JSON.stringify(body),
            headers: {
                "Content-type": "application/json",
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

export const deleteService = async (token, serviceId) => {
    const body = {
        serviceId: serviceId,
        serviceName: "",
        description: "",
        timeRequired: "",
        businessID: null,
        subCategoryID: null
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/deleteService`,
        {
            method: "delete",
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

export const getAllServices = async (token) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/allServices`,
                {
                    method: "get",
                    headers: {
                        "Content-type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            )
        ).json();
        return re.services;
    } catch (err) {
        console.error({ err });
        return [];
    }
};

export const getService = async (token, serviceId) => {
    try {
        const re = await (
            await fetch(
                `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/getService?serviceId=${serviceId}`,
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