import { Button } from "@/components/ui/button";
import SubCategoriesTable from "./components/SubCategoriesTable";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { MoveLeft } from "lucide-react";
import { getAllSubCategoriesForCategoryId } from "@/services/subCategoriesServices";

const subcategory = async (context) => {
    const categoryID = Number(context.params.categoryID);
    const session = await getServerSession(authOptions);
    const data = await getAllSubCategoriesForCategoryId(
        session.apiToken,
        categoryID
    );
    const categoryName = data.categories[0].name;
    return (
        <div className="py-8 px-16">
            <h4 className="text-xs mb-1">
                <a href="/admin/category">
                    <div className="flex items-center">
                        <MoveLeft size={20} />
                        <span className="ml-1 hover:underline">
                            View all categories
                        </span>
                    </div>
                </a>
            </h4>
            <h2 className="text-4xl font-semibold tracking-tight">
                {categoryName}&apos;s categories
            </h2>
            <div className="flex justify-end">
                <Button asChild={true}>
                    <a
                        href={`/admin/category/${categoryID}/subcategory/create`}
                    >
                        Add Sub Category
                    </a>
                </Button>
            </div>
            <SubCategoriesTable
                category_id={categoryID}
                data={data}
                categoryName={categoryName}
            />
        </div>
    );
};

export default subcategory;
