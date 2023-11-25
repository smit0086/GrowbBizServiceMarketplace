"use client";

import * as React from "react";
import ServiceForm from "./ServiceForm";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircle, MoveLeft } from "lucide-react";
import { Button } from "@/components/ui/button";

const AddService = ({
    authSession,
    fetchedServices,
    cancelButton,
    services,
    setServices,
    setRenderAddService,
    formDefaults,
    setFormDefaults,
    businessId,
    subCategories,
}) => {
    const title = "Add Service";
    const subtitle = "Please add a service for your business.";
    const buttonText = "Add";

    const fetchedServiceNames = fetchedServices.map(
        (service) => service.serviceName
    );
    const predefinedServices = subCategories
        .filter(
            (subcategory) => !fetchedServiceNames.includes(subcategory.name)
        )
        .map((subcategory) => {
            return {
                predefinedServiceId: subcategory.subCategoryID,
                predefinedServiceName: subcategory.name,
            };
        });

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
                    Add service
                </h2>
            </div>
            {predefinedServices.length != 0 ? (
                <ServiceForm
                    authSession={authSession}
                    predefinedServices={predefinedServices}
                    cancelButton={cancelButton}
                    services={services}
                    setServices={setServices}
                    setRenderServiceForm={setRenderAddService}
                    formDefaults={formDefaults}
                    setFormDefaults={setFormDefaults}
                    title={title}
                    subtitle={subtitle}
                    buttonText={buttonText}
                    businessId={businessId}
                />
            ) : (
                <div>
                    <div style={centeringStyles}>
                        <Alert
                            variant="destructive"
                            style={{ maxWidth: "30%" }}
                        >
                            <AlertCircle className="h-4 w-4" />
                            <AlertTitle>Warning</AlertTitle>
                            <AlertDescription>
                                There are no more services to add.
                            </AlertDescription>
                        </Alert>
                    </div>
                </div>
            )}
        </div>
    );
};

export default AddService;

const centeringStyles = {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    height: "50vh",
};
