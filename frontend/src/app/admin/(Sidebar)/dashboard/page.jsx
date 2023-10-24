import Navbar from "@/components/modules/Navbar/Navbar";
import { redirect } from "next/navigation";
import React from "react";

const AdminDashboard = () => {
    redirect("/admin/business/verify");
    return (
        <>
            <div>AdminDashboard</div>
        </>
    );
};

export default AdminDashboard;
