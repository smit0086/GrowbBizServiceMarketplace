import React from "react";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import BusinessForm from "@/components/modules/BusinessForm/BusinessForm";

const CreateBussiness = async () => {
    const session = await getServerSession(authOptions);
    const categoryResponse = await (
        await fetch(`${process.env.SERVER_ADDRESS}/admin/allCategories`, {
            method: "get",
            headers: {
                "Content-type": "application/json",
                Authorization: `Bearer ${session.apiToken}`,
            },
        })
    ).json();
    const categories = categoryResponse.categories;
    return (
        <div>
            <BusinessForm categories={categories} />
        </div>
    );
};

export default CreateBussiness;
