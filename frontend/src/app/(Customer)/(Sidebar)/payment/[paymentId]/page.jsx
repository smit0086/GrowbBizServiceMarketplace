import React from "react";
import { MoveLeft } from "lucide-react";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { getPaymentDetail } from "@/services/paymentService";
import { getServerSession } from "next-auth";
import { BadgePlus, BadgeCheck } from "lucide-react";
import { Label } from "@/components/ui/label";
import { getService } from "@/services/servicesService";

const page = async (props) => {
    const authSession = await getServerSession(authOptions);
    const paymentDetails = await getPaymentDetail(
        authSession.apiToken,
        props.params.paymentId
    );
    const service = await getService(
        authSession.apiToken,
        paymentDetails.serviceId
    );
    return (
        <div className="py-8 px-16">
            <h4 className="text-sm mb-1">
                <a href="/payment">
                    <div className="flex items-center">
                        <MoveLeft size={20} />
                        <span className="ml-1 hover:underline">
                            View all payments
                        </span>
                    </div>
                </a>
            </h4>
            <h2 className="text-3xl">Payment details</h2>
            <Card className="w-[500px] my-8">
                <CardHeader className="flex items-center">
                    {paymentDetails.paymentStatus === "CREATED" ? (
                        <>
                            <BadgePlus size={64} color="#34abeb" />
                            <div className="text-xl">Payment Initiated</div>
                        </>
                    ) : (
                        <>
                            <BadgeCheck size={64} color="#34eb3d" />
                            <div className="text-xl">Payment Completed</div>
                        </>
                    )}
                </CardHeader>
                <CardContent>
                    <div>
                        <Label>Service name: </Label>
                        <span>
                            <a
                                href={`/service/${paymentDetails.serviceId}/booking`}
                                className="hover:underline"
                            >
                                {service.services[0].serviceName}
                            </a>
                        </span>
                    </div>
                    <div>
                        <Label>User email: </Label>
                        <span>{paymentDetails.userEmail}</span>
                    </div>
                    <div>
                        <Label>Booking Date: </Label>
                        <span>{paymentDetails.date}</span>
                    </div>
                    <div>
                        <Label>Start time: </Label>
                        <span>
                            {paymentDetails.startTime[0]}:
                            {paymentDetails.startTime[1]}
                        </span>
                    </div>
                    <div>
                        <Label>End time: </Label>
                        <span>
                            {paymentDetails.endTime[0]}:
                            {paymentDetails.endTime[1]}
                        </span>
                    </div>
                    <div>
                        <Label>Amount: </Label>
                        <span>$ {paymentDetails.amount}</span>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
};

export default page;
