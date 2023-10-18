"use client";
import React from "react";
import BusinessForm from "./BusinessForm";
import { createBusiness, updateBusiness } from "@/services/businessService";

const UpdateBusinessForm = ({
    categories,
    failureReason,
    formDefaults,
    businessId,
}) => {
    const FORM_TITLE = "Business verification failure";
    const SUBTITLE =
        "Please read message below carefully and update your business details";
    const BUTTON_TEXT = "Update";
    return (
        <BusinessForm
            categories={categories}
            title={FORM_TITLE}
            subtitle={SUBTITLE}
            buttonText={BUTTON_TEXT}
            databaseMutator={updateBusiness}
            failureReason={failureReason}
            formDefaults={formDefaults}
            businessId={businessId}
        />
    );
};

export default UpdateBusinessForm;
