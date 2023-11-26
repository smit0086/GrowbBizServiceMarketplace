import moment from "moment";
import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getService } from "@/services/servicesService";
import { getFreeTimeSlots } from "@/services/bookingService";
import { MoveLeft } from "lucide-react";
import { AspectRatio } from "@/components/ui/aspect-ratio";
import Image from "next/image";
import { NO_IMAGE_PATH } from "@/lib/constants";
import { getAllBusinesses } from "@/services/businessService";
import ServiceCheckoutForm from "./components/ServiceCheckoutForm";
import ReviewsSection from "./components/ReviewsSection";

export default async function ServiceBooking(context) {
    const authSession = await getServerSession(authOptions);
    const serviceId = Number(context.params.serviceId);
    const serviceResponse = await getService(authSession.apiToken, serviceId);
    const service = serviceResponse.services[0];
    const averageRating =
        serviceResponse.avgRatings[0] !== "NaN"
            ? serviceResponse.avgRatings[0]
            : 0;
    const tax = serviceResponse.tax;
    const todayDate = moment().utc().format("YYYY-MM-DD");
    const freeTimeSlots = await getFreeTimeSlots(
        authSession.apiToken,
        serviceResponse.businessId,
        service.serviceId,
        todayDate
    );
    const businesses = await getAllBusinesses(authSession.apiToken);
    const business = businesses.find(
        (b) => b.businessId === serviceResponse.businessId
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
    const totalPrice = service.price + service.price * (tax / 100);
    return (
        <div className="py-8 px-16">
            <h4 className="text-xs mb-1">
                <a href={`/dashboard`}>
                    <div className="flex items-center">
                        <MoveLeft size={20} />
                        <span className="ml-1 hover:underline">
                            Explore other services
                        </span>
                    </div>
                </a>
            </h4>
            <h2 className="text-4xl font-semibold tracking-tight">
                Service booking
            </h2>
            <div className="my-16 flex">
                <div className="w-[600px] min-w-[400px] rounded overflow-hidden">
                    <AspectRatio ratio={1}>
                        <Image
                            src={service.imageURL || NO_IMAGE_PATH}
                            className="object-cover max-w-[600px] max-h-[600px]"
                            width={600}
                            height={600}
                        />
                    </AspectRatio>
                </div>
                <div className="ml-16 grow">
                    <h2 className="text-2xl">{service.serviceName}</h2>
                    <div className="text-xl">from</div>
                    <h2 className="text-5xl">{business.businessName}</h2>
                    <div className="text-2xl my-6">
                        $ {totalPrice}{" "}
                        <span className="text-xs">
                            (${service.price} + {tax}%)
                        </span>
                    </div>
                    <div className="my-6">
                        <span className="text-xl font-semibold">
                            About this service
                        </span>
                        <div>{service.description}</div>
                    </div>
                    <div className="my-6">
                        <span className="text-xl font-semibold">
                            Avergate rating
                        </span>
                        <div>{averageRating}</div>
                    </div>
                    <ServiceCheckoutForm
                        service={service}
                        availableDates={availableDates}
                        availableTimeSlots={availableTimeSlots}
                    />
                </div>
            </div>
            <ReviewsSection serviceId={serviceId} />
        </div>
    );
}
