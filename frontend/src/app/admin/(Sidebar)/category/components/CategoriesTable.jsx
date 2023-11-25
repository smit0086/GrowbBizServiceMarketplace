import React from "react";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getAllCategories } from "@/services/categoriesServices";
import { Button } from "@/components/ui/button";
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table";

export async function CategoriesTable({ className, ...props }) {
    const session = await getServerSession(authOptions);
    const categories = await getAllCategories(session.apiToken);
    return (
        <Table className="my-8">
            <TableCaption>A list of all the Categories present</TableCaption>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">Category Name</TableHead>
                    <TableHead>Tax % </TableHead>
                    <TableHead>Edit</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {categories.map((item) => (
                    <TableRow key={item.categoryID}>
                        <TableCell className="font-medium w-[250px]">
                            <a
                                href={`/admin/category/${item.categoryID}/subcategory`}
                                className="hover:underline"
                            >
                                {item.name}
                            </a>
                        </TableCell>
                        <TableCell className="font-medium">
                            {item.tax}
                        </TableCell>
                        <TableCell className="font-medium">
                            <Button asChild>
                                <a
                                    href={`/admin/category/update/${item.categoryID}`}
                                >
                                    Edit
                                </a>
                            </Button>
                        </TableCell>
                        <td></td>
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    );
}

export default CategoriesTable;
