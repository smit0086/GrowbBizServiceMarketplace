"use client";
import { usePathname } from "next/navigation";
import { Icons } from "@/components/icons";
import Link from "next/link";
import React from "react";

const NavItem = ({ icon, route, iconClassNames }) => {
    const Component = Icons[icon];
    const path = usePathname();
    return (
        <a href={route} className="inline-block mt-4">
            <Component
                className={iconClassNames ?? undefined}
                isFilled={path.startsWith(route)}
            />
        </a>
    );
};

export default NavItem;
