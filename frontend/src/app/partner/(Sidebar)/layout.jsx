import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import Navbar from "@/components/modules/Navbar/Navbar";
import { getBusiness } from "@/services/businessService";
import { getServerSession } from "next-auth";
import React from "react";

const layout = async ({ children }) => {
    let business = [];
    const session = await getServerSession(authOptions);
    business = await getBusiness(session.user.email, session.apiToken);
    business = business?.businesses[0];
    const partnerNavItems = [
        {
            title: "Booked services",
            route: `/partner/business/${business.businessId}/upcoming-bookings`,
            routeGroup: [
                `/partner/business/${business.businessId}/upcoming-bookings`,
                `/partner/business/${business.businessId}/past-bookings`,
                `/partner/business/${business.businessId}/ongoing-bookings`,
            ],
            icon: "invoice",
            iconClassNames: "w-10 p-1",
        },
        {
            title: "Services",
            route: `/partner/business/${business.businessId}/service`,
            icon: "service",
            iconClassNames: "w-10 p-1",
        },
        {
            title: "Operating hours",
            route: "/partner/business/operating-hours",
            icon: "clock",
            iconClassNames: "w-10 p-1",
        },
    ];
    return (
        <div className="relative">
            <Navbar navItems={partnerNavItems} />
            <div
                className="min-h-screen"
                style={{ width: "calc(100% - 64px)", marginLeft: "64px" }}
            >
                {children}
            </div>
        </div>
    );
};

export default layout;
