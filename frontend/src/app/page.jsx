import { redirect } from "next/navigation";
import { COPY } from "@/lib/constants";

export default function Home() {
    redirect("/dashboard");
    return <main>{COPY.APP_NAME}</main>;
}
