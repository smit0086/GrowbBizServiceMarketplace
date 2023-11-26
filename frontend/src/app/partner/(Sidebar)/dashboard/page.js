import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import Navbar from "@/components/modules/Navbar/Navbar";
import { getBusiness } from "@/services/businessService";
import { getServerSession } from "next-auth";
import { redirect } from "next/navigation";
import React from "react";

const PartnerDashboard = async () => {
    const session = await getServerSession(authOptions);
    let business = await getBusiness(session.user.email, session.apiToken);
    business = business?.businesses[0];
    redirect(`/partner/business/${business.businessId}/upcoming-bookings`);
    return (
        <>
            <div>PartnerDashboard</div>
        </>
    );
};

export default PartnerDashboard;
