export const authenticate = async (email, password, role) => {
    const body = {
        email,
        password,
        role,
    };
    try {
        const resp = await fetch(
            `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/auth/authenticate`,
            {
                method: "post",
                body: JSON.stringify(body),
                headers: {
                    "Content-Type": "application/json",
                },
            }
        );
        if (resp.ok) {
            const data = await resp.json();
            return data;
        }
    } catch (err) {
        console.log("err", err);
    }
};

export const signup = async (firstName, lastName, email, password, role) => {
    const body = JSON.stringify({
        email,
        firstName,
        lastName,
        password,
        role: role,
    });
    try {
        const resp = await fetch(
            `${process.env.NEXT_PUBLIC_SERVER_ADDRESS}/auth/signup`,
            {
                method: "post",
                body,
                headers: {
                    "Content-Type": "application/json",
                },
            }
        );
        if (resp.ok) {
            return await resp.json();
        }
        return false;
    } catch (err) {
        return false;
    }
};


