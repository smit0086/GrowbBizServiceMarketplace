import { NextResponse } from "next/server";
import { getServerSession } from "next-auth/next";
import { authOptions } from "../../auth/[...nextauth]/route";

export async function POST(request) {
    const session = await getServerSession(authOptions);
    const formData = await request.formData();
    const business = await (
        await fetch(`${process.env.SERVER_ADDRESS}/business/save`, {
            method: "post",
            body: formData,
            headers: {
                Authorization: `Bearer ${session.apiToken}`,
            },
        })
    ).json();
    return NextResponse.json(business);
}
