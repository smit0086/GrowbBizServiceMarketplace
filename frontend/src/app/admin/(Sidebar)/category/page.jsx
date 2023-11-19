import { Button } from "@/components/ui/button";
import CategoriesTable from "./components/CategoriesTable";

const category = () => {
    return (
        <>
            {/* <div>Business Categories</div> */}
            <Button style={{position: 'absolute', marginLeft: '550px', top: '5%'}} className="w-[300px]" asChild={true}>
                <a href="/admin/category/create">Add Category</a>
            </Button>
            <CategoriesTable />
        </>
    );
};

export default category;