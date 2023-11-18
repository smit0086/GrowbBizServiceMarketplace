import React from "react";
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table";
import { getAllPayments } from "@/services/paymentService";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { getServerSession } from "next-auth";
import { getAllServices } from "@/services/servicesService";

const AllPayments = async () => {
    const session = await getServerSession(authOptions);
    const payments = await getAllPayments(session.apiToken);
    const services = await getAllServices(session.apiToken);
    const servicesMap = {};
    services.forEach((service) => {
        servicesMap[service.serviceId] = service.serviceName;
    });
    return (
        <div className="py-8 px-16">
            <h2 className="text-3xl">Payments</h2>
            <Table className="my-8">
                <TableCaption>A list of all your payments.</TableCaption>
                <TableHeader>
                    <TableRow>
                        <TableHead className="w-[100px]">Payment ID</TableHead>
                        <TableHead>Status</TableHead>
                        <TableHead>Service</TableHead>
                        <TableHead>Amount</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {payments.map((payment) => (
                        <TableRow key={payment.paymentId}>
                            <TableCell className="font-medium">
                                <a
                                    href={`/payment/${payment.paymentId}`}
                                    className="hover:underline"
                                >
                                    {payment.paymentId}
                                </a>
                            </TableCell>
                            <TableCell>
                                <a
                                    href={`/payment/${payment.paymentId}`}
                                    className="hover:underline"
                                >
                                    {payment.paymentStatus}
                                </a>
                            </TableCell>
                            <TableCell>
                                <a
                                    href={`/payment/${payment.paymentId}`}
                                    className="hover:underline"
                                >
                                    {servicesMap[payment.serviceId]}
                                </a>
                            </TableCell>
                            <TableCell>
                                <a
                                    href={`/payment/${payment.paymentId}`}
                                    className="hover:underline"
                                >
                                    $ {payment.amount}
                                </a>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </div>
    );
};

export default AllPayments;
