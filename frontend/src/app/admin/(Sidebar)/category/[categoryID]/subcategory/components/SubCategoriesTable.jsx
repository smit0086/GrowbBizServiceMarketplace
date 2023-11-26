import React from "react";
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

export async function SubCategoriesTable({
    className,
    category_id,
    data,
    categoryName,
    ...props
}) {
    return (
        <Table className="my-8">
            <TableCaption>
                A list of all the business Subcategories present for{" "}
                {categoryName}.
            </TableCaption>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">
                        Subcategory Name
                    </TableHead>
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
                                <a
                                    href={`/admin/category/${category_id}/subcategory/update/${item.subCategoryID}`}
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

export default SubCategoriesTable;
