"use client";

import Navbar from "@/components/modules/Navbar/Navbar";
import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { categoryForm } from "./components/categoryForm";
import { categoriesTable } from "./components/categoryForm";

const category = () => 
{
    const [showComponent, setShowComponent] = useState(false);

    const callForm = () => {
        setShowComponent(!showComponent);
    };

    return (
        <>
            <Navbar />
            <div>Business Categories</div>
            <Button onClick={callForm} text="Add Category"/>
            <categoriesTable/>
        </>
    );
};

export default category;
