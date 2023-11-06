"use client";

import * as React from "react";
import ServiceForm from "./ServiceForm";

const UpdateService = ({ predefinedServices, cancelButton, services, setServices, setRenderUpdateService, formDefaults, setFormDefaults }) => {
    const title = "Update Service";
    const subtitle = "Please update a service for your business.";
    const buttonText = "Update";

    return (
        <>
            <ServiceForm
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
            />
        </>
    );
}

export default UpdateService;