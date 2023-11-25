"use client";
import { usePathname } from "next/navigation";
import React from "react";

const SubmenuNavItem = ({ link, title }) => {
    const path = usePathname();
    const lastPathItem = path.split("/").pop();
    const isActive = lastPathItem === link.split("/").pop();
    return (
        <a href={link} className={isActive && "font-semibold"}>
            {title}
        </a>
    );
};

export default SubmenuNavItem;
