import React from "react";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getAllCategories } from "@/services/categoriesServices";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import DeleteCategory from "./DeleteCategory";
import category from "../page";

export async function CategoriesTable({ className, ...props }) {
    const session = await getServerSession(authOptions);
    console.log("Test");
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
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>{item.name}</td>
                            <td>{item.tax}</td>
                            <td>
                                <Button asChild>
                                    <Link
                                        href={`/admin/category/update/${item.id}`}
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
