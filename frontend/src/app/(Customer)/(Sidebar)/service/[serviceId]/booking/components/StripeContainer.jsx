"use client";
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import CheckoutForm from "./CheckoutForm";
import { useEffect, useRef, useState } from "react";
import { Skeleton } from "@/components/ui/skeleton";
import { useSession } from "next-auth/react";
import { createPaymentIntent } from "@/services/paymentService";

const STRIPE_PUBLIC_KEY =
    "pk_test_51O9V7jI2En4LSMTzVrQdOIAhnRKqrQdPalwosQi3VlRcMAkdKZXgOZlFzP9zwc6HbV9N5MIjL6v0UQF4ALz4uF0600eLquL9wr";

const stripePromise = loadStripe(STRIPE_PUBLIC_KEY);

export const StripeContainer = ({ formData, closeDialog }) => {
    const [clientSecret, setClientSecret] = useState(null);
    const [paymentId, setPaymentId] = useState(null);
    const requestSignalRef = useRef(null);
    const session = useSession();
    useEffect(() => {
        requestSignalRef.current = new AbortController();
        console.log("signal", requestSignalRef.current.signal);
        const fetchClientSecret = async () => {
            if (!formData) return;
            const response = await createPaymentIntent(
                formData,
                session.data.apiToken,
                requestSignalRef.current.signal
            );
            if (response) {
                setClientSecret(response.clientSecret);
                setPaymentId(response.paymentId);
            }
        };
        fetchClientSecret();
        return () => {
            if (requestSignalRef.current) {
                console.log("aborting");
                requestSignalRef.current.abort();
                requestSignalRef.current = null;
            }
        };
    }, []);
    const options = {
        clientSecret: clientSecret,
    };
    if (options.clientSecret === null)
        return (
            <div>
                <div className="mb-4">
                    <Skeleton className="h-4 w-[75px] mb-1" />
                    <Skeleton className="h-10 w-[350px]" />
                </div>
                <div className="flex mb-4">
                    <div className="mr-2">
                        <Skeleton className="h-4 w-[50px] mb-1" />
                        <Skeleton className="h-10 w-[170px]" />
                    </div>
                    <div>
                        <Skeleton className="h-4 w-[50px] mb-1" />
                        <Skeleton className="h-10 w-[170px]" />
                    </div>
                </div>
                <div className="mb-4">
                    <Skeleton className="h-4 w-[75px] mb-1" />
                    <Skeleton className="h-10 w-[350px]" />
                </div>
                <div className="mb-4">
                    <Skeleton className="h-4 w-[75px] mb-1" />
                    <Skeleton className="h-10 w-[350px]" />
                </div>
            </div>
        );
    return (
        <Elements stripe={stripePromise} options={options}>
            <CheckoutForm
                closeDialog={closeDialog}
                formData={formData}
                paymentId={paymentId}
            />
        </Elements>
    );
};
