import { Button } from "@/components/ui/button";
import CategoriesTable from "./components/CategoriesTable";

const category = () => {
    return (
        <div className="py-8 px-16">
            <h2 className="text-4xl font-semibold tracking-tight">
                Business categories
            </h2>
            <div className="flex justify-end">
                <Button asChild={true}>
                    <a href="/admin/category/create">Add Category</a>
                </Button>
            </div>
            <CategoriesTable />
        </div>
    );
};

export default category;
