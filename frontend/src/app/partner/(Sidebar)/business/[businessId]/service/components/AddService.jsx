"use client";

import * as React from "react";
import ServiceForm from "./ServiceForm";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircle } from "lucide-react";
import { Button } from "@/components/ui/button";

const AddService = ({ authSession, fetchedServices, cancelButton, services, setServices, setRenderAddService, formDefaults, setFormDefaults, businessId, subCategories }) => {
    const title = "Add Service";
    const subtitle = "Please add a service for your business.";
    const buttonText = "Add";

    const fetchedServiceNames = fetchedServices.map(service => service.serviceName);
    const predefinedServices = subCategories
        .filter(subcategory => !fetchedServiceNames.includes(subcategory.name))
        .map((subcategory) => {
            return {
                predefinedServiceId: subcategory.subCategoryID,
                predefinedServiceName: subcategory.name,
            };
        });

    return (
        <div>
            {predefinedServices.length != 0 ?
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
                :
                <div>
                    <div className="flex justify-between items-center">
                        <h2 className="text-3xl p-8 pl-16">Add Service</h2>
                        <Button type="button" onClick={() => window.location.href = `/partner/business/${businessId}/service`} style={{ marginTop: '1%', marginRight: '1%' }}>View</Button>
                    </div>
                    <div style={centeringStyles}>
                        <Alert variant="destructive" style={{ maxWidth: "30%" }}>
                            <AlertCircle className="h-4 w-4" />
                            <AlertTitle>Warning</AlertTitle>
                            <AlertDescription>
                                There are no more services to add.
                            </AlertDescription>
                        </Alert>
                    </div>
                </div>
            }
        </div>
    );
}

export default AddService;

const centeringStyles = {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    height: "50vh",
};