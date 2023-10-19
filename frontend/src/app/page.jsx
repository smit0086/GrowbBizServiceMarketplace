import { redirect } from "next/navigation";
import { COPY, ROLES } from "@/lib/constants";
import { getServerSession } from "next-auth/next";
import { authOptions } from "./api/auth/[...nextauth]/route";

export default async function Home() {
    const session = await getServerSession(authOptions);
    console.log(session);
    if (session.user.role === ROLES.CUSTOMER) {
        redirect("/dashboard");
    } else if (session.user.role === ROLES.PARTNER) {
        redirect("/partner/dashboard");
    } else if (session.user.role === ROLES.ADMIN) {
        redirect("/admin/dashboard");
    }
    return <main>{COPY.APP_NAME}</main>;
}
