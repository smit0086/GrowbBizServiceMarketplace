"use client";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import {
    PaymentElement,
    useElements,
    useStripe,
} from "@stripe/react-stripe-js";
import React, { useState } from "react";

const CheckoutForm = (props) => {
    const stripe = useStripe();
    const elements = useElements();

    const [message, setMessage] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async () => {
        if (!stripe || !elements) {
            // Stripe.js hasn't yet loaded.
            // Make sure to disable form submission until Stripe.js has loaded.
            return;
        }

        setIsLoading(true);

        const { error, paymentIntent } = await stripe.confirmPayment({
            elements,
            redirect: "if_required",
        });
        if (paymentIntent?.status === "succeeded") {
            const URL = `${window.location.origin}/payment/${props.paymentId}`;
            setTimeout(() => {
                window.location.href = URL;
            }, 1000);
        }
        if (error)
            if (
                error.type === "card_error" ||
                error.type === "validation_error"
            ) {
                // This point will only be reached if there is an immediate error when
                // confirming the payment. Otherwise, your customer will be redirected to
                // your `return_url`. For some payment methods like iDEAL, your customer will
                // be redirected to an intermediate site first to authorize the payment, then
                // redirected to the `return_url`.
                setMessage(error.message);
            } else {
                setMessage("An unexpected error occurred.");
            }

        setIsLoading(false);
    };
    const paymentElementOptions = {
        layout: "tabs",
    };

    return (
        <>
            <PaymentElement
                id="payment-element"
                options={paymentElementOptions}
            />
            {message && <p className="text-sm text-destructive">{message}</p>}
            <Button
                disabled={isLoading || !stripe || !elements}
                id="submit"
                onClick={handleSubmit}
            >
                {isLoading && (
                    <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                )}
                Pay now
            </Button>
            <Button variant="outline" onClick={props.closeDialog}>
                Cancel
            </Button>
        </>
    );
};

export default CheckoutForm;
