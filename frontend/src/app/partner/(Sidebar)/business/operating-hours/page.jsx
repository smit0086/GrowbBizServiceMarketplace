import { getBusiness, getBusinessHours } from "@/services/businessService";
import OperatingHoursForm from "./components/OperatingHoursForm";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getServerSession } from "next-auth";
import { DAY_OF_WEEK } from "@/lib/constants";

const minutesFromTuple = (tuple) => {
    const hoursToMinute = tuple[0] * 60;
    return hoursToMinute + tuple[1];
};

const generateStateFromServerResponse = (businessHours) => {
    const respone = {};
    DAY_OF_WEEK.forEach((day, index) => {
        const start_key = `${day.toLowerCase()}_start`;
        const end_key = `${day.toLowerCase()}_end`;
        if (businessHours[start_key] && businessHours[end_key]) {
            respone[day] = {
                start: minutesFromTuple(businessHours[start_key]),
                end: minutesFromTuple(businessHours[end_key]),
            };
        } else {
            respone[day] = null;
        }
    });
    return respone;
};

const OperatingHours = async () => {
    const session = await getServerSession(authOptions);
    const business = (await getBusiness(session.user.email, session.apiToken))
        ?.businesses?.[0];
    const businessHoursResponse = await getBusinessHours(
        session.apiToken,
        business.businessId
    );
    const businessHours = generateStateFromServerResponse(
        businessHoursResponse.businessHour
    );

    return (
        <div>
            <h2 className="text-4xl font-semibold tracking-tight p-8 pl-16">
                Operating hours
            </h2>
            <OperatingHoursForm
                businessHours={businessHours}
                businessId={business.businessId}
            />
        </div>
    );
};

export default OperatingHours;
