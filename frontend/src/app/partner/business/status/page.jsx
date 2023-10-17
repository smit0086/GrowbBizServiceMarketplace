import { PendingVerification } from "@/app/partner/business/status/components/PendingVerification";
import { ApprovedVerification } from "@/app/partner/business/status/components/ApprovedVerification";
import { DeclinedVerification } from "@/app/partner/business/status/components/DeclinedVerification";
import Navbar from "@/components/modules/Navbar/Navbar";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getBusinessByEmail } from "@/services/businessService";
import { BUSINESS_VERIFICATION_STATUS } from "@/lib/constants";

export default async function ProviderBusinessStatus() {
    const authSession = await getServerSession(authOptions);
    const businesses = (await getBusinessByEmail(authSession.apiToken, authSession.user.email)).businesses;

    return (
        <>
            <Navbar callbackUrl="/partner/login" />
            <div style={centeringStyles}>
                {businesses[0].status === BUSINESS_VERIFICATION_STATUS.PENDING ?
                    <PendingVerification />
                    :
                    <></>
                }
                {businesses[0].status === BUSINESS_VERIFICATION_STATUS.APPROVED ?
                    <ApprovedVerification />
                    :
                    <></>
                }
                {businesses[0].status === BUSINESS_VERIFICATION_STATUS.DECLINED ?
                    <DeclinedVerification business={businesses[0]} />
                    :
                    <></>
                }
            </div>
        </>
    );
}

const centeringStyles = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    height: '50vh'
};