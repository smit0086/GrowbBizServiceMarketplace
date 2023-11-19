import { Button } from "@/components/ui/button";
import SubCategoriesTable from "./components/SubCategoriesTable";

const subcategory = (context) => {
    const categoryID = Number(context.params.categoryID)
    return (
        <>
            {/* <div>Business SubCategories</div>   */}
            <Button style={{position: 'absolute', marginLeft: '760px', top: '6%'}} className="w-[300px]" asChild={true}>
                <a href={`/admin/category/${categoryID}/subcategory/create`}>Add Sub Category</a>
            </Button>          
            <SubCategoriesTable category_id={categoryID} />
        </>
    );
};

export default subcategory;
