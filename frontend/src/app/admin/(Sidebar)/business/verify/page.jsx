import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";

import PendingBusinessesLoader from "./components/PendingBusinessesLoader";
import { getAllCategories } from "@/services/categoryService";

export default async function BusinessApproval() {
    const authSession = await getServerSession(authOptions);
    const categories = await getAllCategories(authSession.apiToken);

    return (
        <div className="py-8 px-16">
            <h2 className="text-4xl font-semibold tracking-tight">
                Business verification
            </h2>
            <PendingBusinessesLoader
                authSession={authSession}
                categories={categories}
            />
        </div>
    );
}
