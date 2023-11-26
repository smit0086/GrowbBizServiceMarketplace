import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { UpdateSubForm } from "../../components/UpdateSubForm";
import { getAllCategories } from "@/services/categoriesServices";
import { MoveLeft } from "lucide-react";

const updatesubcategory = async (context) => {
    const authSession = await getServerSession(authOptions);
    const subcategoryID = context.params.subcategoryID;
    const categoryID = context.params.categoryID;
    const categories = await getAllCategories(authSession.apiToken);

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
                Update subcategory
            </h2>
            <UpdateSubForm
                subcategoryID={subcategoryID}
                categories={categories}
                categoryID={categoryID}
            />
        </div>
    );
};

export default updatesubcategory;
