import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

import PendingBusinessesLoader from "./components/PendingBusinessesLoader";
import { getAllCategories } from "@/services/categoryService";

export default async function BusinessApproval() {
    const authSession = await getServerSession(authOptions);
    const categories = await getAllCategories(authSession.apiToken);

    return (
        <>
            <PendingBusinessesLoader
                authSession={authSession}
                categories={categories}
            />
        </>
    );
}
