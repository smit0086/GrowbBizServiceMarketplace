import { redirect } from "next/navigation";
import { COPY } from "@/lib/constants";
import { getServerSession } from "next-auth/next";
import { authOptions } from "./api/auth/[...nextauth]/route";

export default async function Home() {
    const session = await getServerSession(authOptions);
    console.log("ses", session);
    if (session.user.role === "CUSTOMER") {
        redirect("/dashboard");
    } else if (session.user.role === "PARTNER") {
        redirect("/partner/dashboard");
    } else if (session.user.role === "ADMIN") {
        redirect("/admin/dashboard");
    }
    return <main>{COPY.APP_NAME}</main>;
}
