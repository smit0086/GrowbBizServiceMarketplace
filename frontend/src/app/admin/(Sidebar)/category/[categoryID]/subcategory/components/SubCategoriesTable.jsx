import React from "react";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import DeleteSubCategory from "./DeleteSubCategory";
import subcategory from "../page";
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table";
import { getAllSubCategoriesForCategoryId } from "@/services/subCategoriesServices";

export async function SubCategoriesTable({ className, category_id, ...props }) {
    const session = await getServerSession(authOptions);
    const data = await getAllSubCategoriesForCategoryId(session.apiToken, category_id);
    console.log(data)
    const categoryName = data.categories[0].name;
    console.log(categoryName)
    return (
        <div className="py-8 px-16">
        <h2 className="text-3xl">{categoryName}&apos; Sub Categories</h2>
        <Table className="my-8">
            <TableCaption>A list of all the business Subcategories present for {categoryName}.</TableCaption>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">Subcategory Name</TableHead>
                    <TableHead>Parent Category</TableHead>
                    <TableHead>Edit</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {data.subCategories.map((item) => (
                    <TableRow key={item.subCategoryID}>  
                        <TableCell className="font-medium w-[250px]">
                            {item.name}
                        </TableCell>
                        <TableCell className="font-medium">
                            {categoryName}
                        </TableCell>
                        <TableCell className="font-medium">
                            <Button asChild>
                                <Link
                                    href={`/admin/category/${category_id}/subcategory/update/${item.subCategoryID}`}
                                >
                                    Edit
                                </Link>
                            </Button>
                        </TableCell>   
                            <td>
                                
                            </td>
                            {/* <td>
                                <DeleteSubCategory subcategory={item} />
                            </td> */}
                        </TableRow>
                    ))}
            </TableBody>
        </Table>
    </div>
    );
}

export default SubCategoriesTable;
