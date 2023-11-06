import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import Navbar from "@/components/modules/Navbar/Navbar";
import ViewServices from "./components/ViewServices";

export default async function ServiceManagement() {
    const authSession = await getServerSession(authOptions);
    const predefinedServices = [
        {
            predefinedServiceId: 1,
            predefinedServiceName: "Service 1"
        },
        {
            predefinedServiceId: 2,
            predefinedServiceName: "Service 2"
        },
        {
            predefinedServiceId: 3,
            predefinedServiceName: "Service 3"
        }
    ];

    return (
        <>
            <Navbar callbackUrl="/partner/login" />
            <ViewServices authSession={authSession} predefinedServices={predefinedServices} />
        </>
    );
}