import { MoveLeft } from "lucide-react";
import { CategoryForm } from "../components/CategoryForm";

const addcategory = () => {
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
                Add category
            </h2>
            <CategoryForm />
        </div>
    );
};

export default addcategory;
