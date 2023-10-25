
import Navbar from "@/components/modules/Navbar/Navbar";
import { CategoryForm } from "../components/CategoryForm";

const addcategory = () => 
{
    return (
        <>
            <Navbar />
            <div>Add Business Categories</div>
            <CategoryForm />
        </>
    );
};

export default addcategory;
