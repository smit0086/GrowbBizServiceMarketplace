"use client";

import * as React from "react";
import { useEffect, useState } from "react";
import {
    Alert,
    AlertDescription,
    AlertTitle,
} from "@/components/ui/alert";
import { Terminal } from "lucide-react";
import BusinessApprovalForm from "@/app/admin/business/verify/components/BusinessApprovalForm";
import { getBusinessesByPendingStatus } from "@/services/businessService";

const PendingBusinessesLoader = ({ authSession, categories }) => {
    const [businesses, setBusinesses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [refreshBusinesses, setRefreshBusinesses] = useState(false);

    useEffect(() => {
        const fetchBusinesses = async () => {
            const result = await getBusinessesByPendingStatus(authSession.apiToken);
            setBusinesses(result.businesses);
            setLoading(false);
            setRefreshBusinesses(false);
        };

        fetchBusinesses();
    }, [refreshBusinesses]);

    return (
        <>
            {loading ? (
                <div>Loading...</div>
            ) : (
                businesses.length === 0 ? (
                    <div style={{ marginTop: '1%', marginLeft: '1%', marginRight: '1%' }}>
                        <Alert>
                            <Terminal className="h-4 w-4" />
                            <AlertTitle>Business Verification</AlertTitle>
                            <AlertDescription>
                                There is no pending verification for any business.
                            </AlertDescription>
                        </Alert>
                    </div>
                ) : (
                    <div className="flex flex-wrap gap-4" style={{ marginTop: '1%', marginLeft: '1%' }}>
                        {businesses.map((business) => (
                            <BusinessApprovalForm key={business.businessId} business={business} authSession={authSession} setRefreshBusinesses={setRefreshBusinesses} categories={categories} />
                        ))}
                    </div>
                )
            )}
        </>
    );
}

export default PendingBusinessesLoader;