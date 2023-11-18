import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { ScrollArea } from "@/components/ui/scroll-area";
import ViewReviews from "./components/ViewReviews";

export default async function ServiceManagement(context) {
    const authSession = await getServerSession(authOptions);
    const serviceId = Number(context.params.serviceId);

    const reviews = [
        {
            userEmail: "vivekcustomer@gmail.com",
            review: "bad service",
            rating: 1.5
        },
        {
            userEmail: "vivekcustomer@gmail.com",
            review: "good service",
            rating: 4
        }
    ];

    return (
        <>
            <ScrollArea className="h-[100vh] w-[95vw] rounded-md p-4">
                <div>
                    <ViewReviews reviews={reviews} />
                </div>
            </ScrollArea>
        </>
    );
}