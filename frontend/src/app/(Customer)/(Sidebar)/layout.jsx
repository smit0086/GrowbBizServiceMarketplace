import React from "react";
import Navbar from "@/components/modules/Navbar/Navbar";

const partnerNavItems = [
    {
        title: "Dashboard",
        route: "/dashboard",
        icon: "layoutGrid",
    },
    {
        title: "Bookings",
        route: "/bookings",
        icon: "invoice",
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
