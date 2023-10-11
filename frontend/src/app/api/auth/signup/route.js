import { NextResponse } from "next/server";

export async function POST(request) {
    const req = await request.json();
    const user = await (
        await fetch(`${process.env.SERVER_ADDRESS}/auth/signup`, {
            method: "post",
            body: JSON.stringify(req),
            headers: {
                "Content-Type": "application/json",
            },
        })
    ).json();
    return NextResponse.json(user);
}
