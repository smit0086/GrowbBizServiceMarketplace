import { getServerSession } from "next-auth";
import { authOptions } from "../../auth/[...nextauth]/route";
import { NextResponse } from "next/server";

export async function GET() {
    const session = await getServerSession(authOptions);
    console.log({ session1: session });
    return NextResponse.json({});
}
