import { NextResponse } from "next/server";

export async function POST(request) {
    const req = await request.json();
    const user = await // TODO: add env path
    (
        await fetch("http://localhost:9002/auth/signup", {
            method: "post",
            body: JSON.stringify(req),
            headers: {
                "Content-Type": "application/json",
            },
        })
    ).json();
    console.log({ user });
    return NextResponse.json(user);
}
