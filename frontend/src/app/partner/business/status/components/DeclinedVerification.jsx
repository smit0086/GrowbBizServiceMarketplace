"use client";

import * as React from "react";
import {
    Alert,
    AlertDescription,
    AlertTitle,
} from "@/components/ui/alert";
import { AlertCircle } from "lucide-react";

export function DeclinedVerification({ business }) {
    return (
        <Alert variant="destructive" style={{ maxWidth: '30%' }}>
            <AlertCircle className="h-4 w-4" />
            <AlertTitle>Declined</AlertTitle>
            <AlertDescription>
                Your business verification is declined.
                <br />
                Rejection reason: {business.reason}
            </AlertDescription>
        </Alert>
    );
}
