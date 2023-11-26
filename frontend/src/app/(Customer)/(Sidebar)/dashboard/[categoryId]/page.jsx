import React from "react";
import { getServerSession } from "next-auth";
import { BadgeInfo, MoveLeft } from "lucide-react";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getCategoryByID } from "@/services/categoriesServices";
import ServiceList from "./components/ServiceList";
import { getAllSubCategoriesForCategory } from "@/services/subCategoriesServices";
import { getAllServiceByCategoryId } from "@/services/servicesService";
import { getAllBusinesses } from "@/services/businessService";
import { NO_IMAGE_PATH } from "@/lib/constants";

const formatServices = (services, businessMap) => {
    return services.map((service) => {
        return {
            ...service,
            imageURL: service.imageURL ?? NO_IMAGE_PATH,
            businessName: businessMap[service.businessId],
        };
    });
};

const CategoryList = async (props) => {
    const categoryId = parseInt(props.params.categoryId);
    const session = await getServerSession(authOptions);
    const subcategories = await getAllSubCategoriesForCategory(
        session.apiToken,
        categoryId
    );
    const category = await getCategoryByID(session.apiToken, categoryId);
    const services = await getAllServiceByCategoryId(
        session.apiToken,
        categoryId
    );
    const businesses = await getAllBusinesses(session.apiToken);
    const businessMap = {};
    businesses.forEach((business) => {
        businessMap[business.businessId] = business.businessName;
    });
    return (
        <div className="p-8 pl-16">
            <a className="text-xs" href="/dashboard">
                <div className="flex items-center">
                    <MoveLeft size={20} />
                    <span className="ml-1 hover:underline">
                        Back to categories
                    </span>
                </div>
            </a>
            <h1 className="text-4xl font-semibold tracking-tight mb-8">
                {category.name}
            </h1>
            {subcategories.length ? (
                <ServiceList
                    subcategories={subcategories}
                    services={formatServices(services, businessMap)}
                    businessMap={businessMap}
                />
            ) : (
                <div className="flex justify-center my-64">
                    <div className="flex items-center flex-col">
                        <BadgeInfo size={64} color="rgb(250, 204, 21)" />
                        <p className="text-2xl my-2">No subcategories found</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default CategoryList;
