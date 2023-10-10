export const authenticate = async (email, password, role) => {
    const body = {
        email,
        password,
        role,
    };
    // TODO: ADD ENV VARIABLE
    const resp = await fetch("http://localhost:9002/auth/authenticate", {
        method: "post",
        body: JSON.stringify(body),
    });
    try {
        const data = await resp.json();
        return data;
    } catch (err) {
        console.log("err", err);
    }
};
