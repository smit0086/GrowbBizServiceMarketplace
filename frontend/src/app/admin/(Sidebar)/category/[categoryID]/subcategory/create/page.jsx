import { MoveLeft } from "lucide-react";
import { SubCategoryForm } from "../components/SubCategoryForm";

const addSubcategory = (context) => {
    const categoryID = context.params.categoryID;
    return (
        <div className="py-8 px-16">
            <h4 className="text-xs mb-1">
                <a href={`/admin/category/${categoryID}/subcategory`}>
                    <div className="flex items-center">
                        <MoveLeft size={20} />
                        <span className="ml-1 hover:underline">
                            View all subcategories
                        </span>
                    </div>
                </a>
            </h4>
            <h2 className="text-4xl font-semibold tracking-tight">
                Add subcategory
            </h2>
            <SubCategoryForm categoryID={categoryID} />
        </div>
    );
};

export default addSubcategory;
