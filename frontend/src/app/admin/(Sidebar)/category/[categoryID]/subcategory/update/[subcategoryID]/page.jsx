import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { UpdateSubForm } from "../../components/UpdateSubForm";
import { getAllCategories } from "@/services/categoriesServices";

const updatesubcategory = async(context) => {
    const authSession = await getServerSession(authOptions);
    const subcategoryID = context.params.subcategoryID;   
    const categoryID = context.params.categoryID 
    const categories = await getAllCategories(authSession.apiToken);
    console.log(categories);

    return (
        <>
            <div>Update Business SubCategories</div>
            <UpdateSubForm subcategoryID={subcategoryID} categories={categories} categoryID={categoryID}
            />
        </>
    );
};

export default updatesubcategory;
