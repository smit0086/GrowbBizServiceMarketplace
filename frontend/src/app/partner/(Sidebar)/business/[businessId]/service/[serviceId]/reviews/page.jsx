import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { ScrollArea } from "@/components/ui/scroll-area";
import ViewReviews from "./components/ViewReviews";
import { getReviewsAndRatingsByServiceId } from "@/services/reviewsAndRatingsService";

export default async function ServiceManagement(context) {
    const authSession = await getServerSession(authOptions);
    const serviceId = Number(context.params.serviceId);

    const reviews = await getReviewsAndRatingsByServiceId(authSession.apiToken, serviceId);

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