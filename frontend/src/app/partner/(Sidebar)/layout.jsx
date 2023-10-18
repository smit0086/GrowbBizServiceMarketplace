import Navbar from "@/components/modules/Navbar/Navbar";
import React from "react";

const partnerNavItems = [
    {
        title: "Operating hours",
        route: "/partner/business/operating-hours",
        icon: "clock",
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
