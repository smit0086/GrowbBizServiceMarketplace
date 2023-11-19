import moment from "moment";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getService } from "@/services/servicesService";
import ServiceBookingForm from "./components/ServiceBookingForm";
import { getFreeTimeSlots } from "@/services/bookingService";

export default async function ServiceBooking(context) {
    const authSession = await getServerSession(authOptions);
    const serviceId = Number(context.params.serviceId);
    const serviceResponse = await getService(authSession.apiToken, serviceId);
    const service = serviceResponse.services[0];
    const tax = serviceResponse.tax;
    const todayDate = moment().utc().format("YYYY-MM-DD");
    const freeTimeSlots = await getFreeTimeSlots(
        authSession.apiToken,
        serviceResponse.businessId,
        service.serviceId,
        todayDate
    );
    let availableDates = Object.keys(freeTimeSlots).map((date) =>
        date.substring(0, 10)
    );
    availableDates = availableDates.filter(
        (date) => date >= new Date().toLocaleDateString("en-CA")
    );

    const availableTimeSlots = {};

    for (const date in freeTimeSlots) {
        const timeSlots = freeTimeSlots[date].map((slot) => {
            const startTime = `${String(slot.startTime[0]).padStart(
                2,
                "0"
            )}:${String(slot.startTime[1]).padStart(2, "0")}`;
            const endTime = `${String(slot.endTime[0]).padStart(
                2,
                "0"
            )}:${String(slot.endTime[1]).padStart(2, "0")}`;
            return `${startTime} - ${endTime}`;
        });

        availableTimeSlots[date.substring(0, 10)] = timeSlots;
    }

    return (
        <>
            <ServiceBookingForm
                authSession={authSession}
                service={service}
                tax={tax}
                availableDates={availableDates}
                availableTimeSlots={availableTimeSlots}
            />
        </>
    );
}
