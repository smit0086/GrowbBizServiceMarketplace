import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import ViewServices from "./components/ViewServices";
import { getAllSubCategories } from "@/services/subCategoriesServices";
import { getBusiness } from "@/services/businessService";
import { getServicesByBusinessId } from "@/services/servicesService";

export default async function ServiceManagement(context) {
    const authSession = await getServerSession(authOptions);
    const businessId = Number(context.params.businessId);
    const subCategories = await getAllSubCategories(authSession.apiToken);

    function convertHHMMToMinutes(formattedTime) {
        const [hours, minutes] = formattedTime;
        const totalMinutes = (hours * 60) + minutes;
        return totalMinutes;
    }

    let fetchedServices = await getServicesByBusinessId(authSession.apiToken, businessId);
    fetchedServices = fetchedServices.map(service => ({
        ...service,
        timeRequired: convertHHMMToMinutes(service.timeRequired),
    }));

    const predefinedServices = subCategories.map(subcategory => {
        return {
            predefinedServiceId: subcategory.subCategoryID,
            predefinedServiceName: subcategory.name
        };
    });

    return (
        <>
            <ViewServices authSession={authSession} predefinedServices={predefinedServices} businessId={businessId} fetchedServices={fetchedServices} />
        </>
    );
}