import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getBusiness } from "@/services/businessService";
import { BUSSINESS_STATUS } from "@/lib/constants";
import { getAllCategories } from "@/services/categoryService";
import UpdateBusinessForm from "@/components/modules/BusinessForm/UpdateBusinessForm";
import { VerificationFailed } from "./components/VerificationFailed";

export default async function ProviderBusinessStatus() {
    const session = await getServerSession(authOptions);
    const business = (await getBusiness(session.user.email, session.apiToken))
        .businesses[0];
    const categories = await getAllCategories(session.apiToken);
    if (business.status === BUSSINESS_STATUS.PENDING) {
        return (
            <div style={centeringStyles}>
                <VerificationFailed />
            </div>
        );
    } else if (business.status === BUSSINESS_STATUS.DECLINED) {
        return (
            <UpdateBusinessForm
                categories={categories}
                failureReason={business.reason}
                formDefaults={{
                    businessName: business.businessName,
                    businessCategory: business.category.categoryID.toString(),
                }}
                businessId={business.businessId}
            />
        );
    }
    return <>{business.status}</>;
}

const centeringStyles = {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    height: "50vh",
};
