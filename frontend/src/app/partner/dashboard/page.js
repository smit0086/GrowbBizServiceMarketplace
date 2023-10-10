import Navbar from "@/components/modules/Navbar/Navbar";
import React from "react";

const PartnerDashboard = () => {
    return (
        <>
            <Navbar callbackUrl="/partner/login" />
            <div>PartnerDashboard</div>
        </>
    );
};

export default PartnerDashboard;
