"use client";

import * as React from "react";
import {
    Alert,
    AlertDescription,
    AlertTitle,
} from "@/components/ui/alert";
import { Terminal  } from "lucide-react";

export function ApprovedVerification({ className, ...props }) {
    return (
        <Alert style={{ maxWidth: '30%' }}>
            <Terminal className="h-4 w-4" />
            <AlertTitle>Approved</AlertTitle>
            <AlertDescription>
                Your business verification is approved.
            </AlertDescription>
        </Alert>
    );
}