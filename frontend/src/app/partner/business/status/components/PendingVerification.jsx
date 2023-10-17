"use client";

import * as React from "react";
import {
    Alert,
    AlertDescription,
    AlertTitle,
} from "@/components/ui/alert";
import { AlertCircle } from "lucide-react";

export function PendingVerification({ className, ...props }) {
    return (
        <Alert variant="destructive" style={{ maxWidth: '30%' }}>
            <AlertCircle className="h-4 w-4" />
            <AlertTitle>Warning</AlertTitle>
            <AlertDescription>
                Your business verification is pending. Please try again later.
            </AlertDescription>
        </Alert>
    );
}
