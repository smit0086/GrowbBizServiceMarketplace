export const authenticate = async (email, password, role) => {
    const body = {
        email,
        password,
        role,
    };
    console.log("body", body);
    // TODO: ADD ENV VARIABLE
    const resp = await fetch("http://localhost:9002/auth/authenticate", {
        method: "post",
        body: JSON.stringify(body),
        headers: {
            "Content-Type": "application/json",
        },
    });
    try {
        const data = await resp.json();
        return data;
    } catch (err) {
        console.log("err", err);
    }
};
