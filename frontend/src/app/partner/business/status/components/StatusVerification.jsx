"use client";

import * as React from "react";
import { useState, useEffect } from "react";
import {
    Alert,
    AlertDescription,
    AlertTitle,
} from "@/components/ui/alert";
import { AlertCircle } from "lucide-react";

export function StatusVerification({ className, ...props }) {
    const [status, setStatus] = useState("");

    useEffect(() => {
        setStatus("pending");
    }, []);

    if (status === "pending") {
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
    else{
        return (
            <>Verified</>
        );
    }

}
