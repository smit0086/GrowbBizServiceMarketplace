"use client";
import React from "react";
import BusinessForm from "./BusinessForm";
import { createBusiness } from "@/services/businessService";

const CreateBusinessForm = ({ categories }) => {
    const FORM_TITLE = "Register your business";
    const SUBTITLE = "Please enter your business details";
    const BUTTON_TEXT = "Register";
    return (
        <BusinessForm
            categories={categories}
            title={FORM_TITLE}
            subtitle={SUBTITLE}
            buttonText={BUTTON_TEXT}
            databaseMutator={createBusiness}
        />
    );
};

export default CreateBusinessForm;
