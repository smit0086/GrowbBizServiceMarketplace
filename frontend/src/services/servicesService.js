export const addService = async (token, serviceName, description, timeRequired, businessID, subCategoryID) => {
    const body = {
        serviceName,
        description,
        timeRequired,
        businessID,
        subCategoryID
    };
    console.log(body);
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/addService`,
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

export const updateService = async (token, serviceId, serviceName, description, timeRequired, businessID, subCategoryID) => {
    const body = {
        serviceID: serviceId,
        serviceName,
        description,
        timeRequired,
        businessID,
        subCategoryID
    };
    const resp = await fetch(
        `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/services/updateService`,
        {
            method: "put",
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