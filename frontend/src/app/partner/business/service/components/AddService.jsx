"use client";

import * as React from "react";
import ServiceForm from "./ServiceForm";

const AddService = ({ predefinedServices, cancelButton, services, setServices, setRenderAddService, formDefaults, setFormDefaults }) => {
    const title = "Add Service";
    const subtitle = "Please add a service for your business.";
    const buttonText = "Add";

    return (
        <>
            <ServiceForm
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
            />
        </>
    );
}

export default AddService;