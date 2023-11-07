import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import Navbar from "@/components/modules/Navbar/Navbar";
import ServiceBookingForm from "./components/ServiceBookingForm";

export default async function ServiceBooking() {
    const authSession = await getServerSession(authOptions);
    const service = {
        serviceId: 1,
        serviceName: 'Hair cut',
        servicePrice: 50,
        serviceTax: 10
    }

    const availableDates = ['2023-10-26', '2023-10-27', '2023-10-28'];

    const availableTimeSlots = [
        '08:00 AM - 08:30 AM',
        '09:00 AM - 09:30 AM',
        '12:00 PM - 12:30 PM',
        '01:00 PM - 01:30 PM'
    ];

    return (
        <>
            <Navbar callbackUrl="/login" />
            <ServiceBookingForm service={service} availableDates={availableDates} availableTimeSlots={availableTimeSlots} />
        </>
    );
}
