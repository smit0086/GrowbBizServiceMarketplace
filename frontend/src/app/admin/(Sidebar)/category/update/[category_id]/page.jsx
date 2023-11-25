import { MoveLeft } from "lucide-react";
import { UpdateForm } from "../../components/UpdateForm";

const updatecategory = (context) => {
    const category_id = context.params.category_id;

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
                Update category
            </h2>
            <UpdateForm category_id={category_id} />
        </div>
    );
};

export default updatecategory;

// export async function getServerSideProps(context) {   const { params } = context;   const { category_id } = params;   // Now you can access the category_id in the server-side rendering context  // You can also perform additional data fetching or processing here   return {     props: {       category_id,     },   }; }
