"use client";

import * as React from "react";
import ServiceForm from "./ServiceForm";

const AddService = ({ authSession, predefinedServices, cancelButton, services, setServices, setRenderAddService, formDefaults, setFormDefaults, businessId }) => {
    const title = "Add Service";
    const subtitle = "Please add a service for your business.";
    const buttonText = "Add";

    return (
        <>
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
        </>
    );
}

export default AddService;