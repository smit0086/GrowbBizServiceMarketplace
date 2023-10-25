import { UpdateForm } from "../../components/UpdateForm";

const updatecategory = (context) => {
    const category_id = context.params.category_id;

    return (
        <>
            <div>Update Business Categories</div>
            <UpdateForm category_id={category_id} />
        </>
    );
};

export default updatecategory;

// export async function getServerSideProps(context) {   const { params } = context;   const { category_id } = params;   // Now you can access the category_id in the server-side rendering context  // You can also perform additional data fetching or processing here   return {     props: {       category_id,     },   }; }
