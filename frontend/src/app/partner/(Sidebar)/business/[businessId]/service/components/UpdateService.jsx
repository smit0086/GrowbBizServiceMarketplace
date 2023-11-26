"use client";

import * as React from "react";
import ServiceForm from "./ServiceForm";
import { MoveLeft } from "lucide-react";

const UpdateService = ({
    authSession,
    predefinedServices,
    cancelButton,
    services,
    setServices,
    setRenderUpdateService,
    formDefaults,
    setFormDefaults,
    businessId,
}) => {
    const title = "Update Service";
    const subtitle = "Please update a service for your business.";
    const buttonText = "Update";

    return (
        <div className="px-16 py-8">
            <div>
                <a
                    className="text-xs"
                    href={`/partner/business/${businessId}/service`}
                >
                    <div className="flex items-center">
                        <MoveLeft size={20} />
                        <span className="ml-1 hover:underline">
                            Back to services
                        </span>
                    </div>
                </a>
                <h2 className="text-4xl font-semibold tracking-tight">
                    Update service
                </h2>
            </div>
            <ServiceForm
                authSession={authSession}
                predefinedServices={predefinedServices}
                cancelButton={cancelButton}
                services={services}
                setServices={setServices}
                setRenderServiceForm={setRenderUpdateService}
                formDefaults={formDefaults}
                setFormDefaults={setFormDefaults}
                title={title}
                subtitle={subtitle}
                buttonText={buttonText}
                businessId={businessId}
            />
        </div>
    );
};

export default UpdateService;
