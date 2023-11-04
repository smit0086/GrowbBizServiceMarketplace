import React from "react";
import { Button } from "@/components/ui/button";
import CategoriesTable from "./components/CategoriesTable";

const category = () => {
    return (
        <>
            <div>Business Categories</div>
            <Button asChild={true}>
                <a href="/admin/category/create">Add category</a>
            </Button>
            <CategoriesTable />
        </>
    );
};

export default category;
