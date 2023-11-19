import { SubCategoryForm } from "../components/SubCategoryForm";

const addSubcategory = (context) => {
    const categoryID = context.params.categoryID;
    return (
        <>
            <div>Add Business Sub Categories</div>
            <SubCategoryForm categoryID={categoryID} />
        </>
    );
};

export default addSubcategory;
