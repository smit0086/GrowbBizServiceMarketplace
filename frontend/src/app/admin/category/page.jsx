"use client";

import Navbar from "@/components/modules/Navbar/Navbar";
import React, { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { CategoriesTable } from "./components/CategoriesTable";

const category = () => 
{
    return (
        <>
            <Navbar />
            <div>Business Categories</div>
            <Button asChild={true}><a href="/admin/category/create" >Add category</a></Button>
            <CategoriesTable />
        </>
    );
};

export default category;
