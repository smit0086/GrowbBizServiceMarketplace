import React from "react";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getAllCategories } from "@/services/categoryService";
import CreateBusinessForm from "@/components/modules/BusinessForm/CreateBusinessForm";

const CreateBussiness = async () => {
    const session = await getServerSession(authOptions);
    const categories = await getAllCategories(session.apiToken);
    return <CreateBusinessForm categories={categories} />;
};

export default CreateBussiness;
