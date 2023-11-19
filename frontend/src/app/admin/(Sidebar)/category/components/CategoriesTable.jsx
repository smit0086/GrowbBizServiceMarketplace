import React from "react";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getAllCategories } from "@/services/categoriesServices";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import DeleteCategory from "./DeleteCategory";
import category from "../page";
import { getAllSubCategoriesForCategoryId } from "@/services/subCategoriesServices";

export async function CategoriesTable({ className, ...props }) {
    const session = await getServerSession(authOptions);
    const categories = await getAllCategories(session.apiToken);
    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Category ID</th>
                        <th>Category Name</th>
                        <th>Tax %</th>
                        <th>edit</th>
                        <th>delete</th>
                    </tr>
                </thead>
                <tbody>
                    {categories.map((item) => (
                        <tr key={item.categoryID}>                            
                            {/* <Button  onClick={() => getAllSubCategoriesForCategoryId(session.apiToken, item.categoryID)}>
                                {item.categoryID}
                            </Button> */}
                            <td>{item.name}</td>
                            <td>{item.tax}</td>
                            <td>
                                <Button asChild>
                                    <Link
                                        href={`/admin/category/update/${item.categoryID}`}
                                    >
                                        Edit
                                    </Link>
                                </Button>
                            </td>
                            <td>
                                <DeleteCategory category={item} />
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default CategoriesTable;
