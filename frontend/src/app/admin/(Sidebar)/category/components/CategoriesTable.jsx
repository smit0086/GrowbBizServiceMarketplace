import React from "react";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getAllCategories } from "@/services/categoriesServices";
import Link from "next/link";
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
    // console.log("Test");
    const categories = await getAllCategories(session.apiToken);
    return (
        <div className="py-8 px-16">
        <h2 className="text-3xl">Main Business Categories</h2>
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
                            <a href={`/admin/category/${item.categoryID}/subcategory`} className="hover:underline">{item.name}</a>
                        </TableCell>
                        <TableCell className="font-medium">
                            {item.tax}
                        </TableCell>
                        <TableCell className="font-medium">
                            <Button asChild>
                                <Link href={`/admin/category/update/${item.categoryID}`} >
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
        // <div>
        //     <table>
        //         <thead>
        //             <tr>
        //                 <th>Category Name</th>
        //                 <th>Tax %</th>
        //                 <th>Edit</th>
        //                 {/* <th>delete</th> */}
        //             </tr>
        //         </thead>
        //         <tbody>
        //             {categories.map((item) => (
        //                 <tr key={item.categoryID}>  
        //                     <td><a href={`/admin/category/${item.categoryID}/subcategory`}>{item.name}</a></td>
        //                     <td>{item.tax}</td>
        //                     <td>
        //                         <Button asChild>
        //                             <Link
        //                                 href={`/admin/category/update/${item.categoryID}`}
        //                             >
        //                                 Edit
        //                             </Link>
        //                         </Button>
        //                     </td>
        //                     {/* <td>
        //                         <DeleteCategory category={item} />
        //                     </td> */}
        //                 </tr>
        //             ))}
        //         </tbody>
        //     </table>
        // </div>
    );
}

export default CategoriesTable;
