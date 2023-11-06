import React from "react";
import Navbar from "@/components/modules/Navbar/Navbar";

const partnerNavItems = [
    {
        title: "Business Verify",
        route: "/admin/business/verify",
        icon: "layoutGrid",
    },
    {
        title: "Business Category",
        route: "/admin/category",
        icon: "badgeCheck",
    },
];

const layout = ({ children }) => {
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
