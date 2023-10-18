"use client";
import { usePathname } from "next/navigation";
import { Icons } from "@/components/icons";
import Link from "next/link";
import React from "react";

const NavItem = ({ icon, route }) => {
    const Component = Icons[icon];
    const path = usePathname();
    return (
        <Link href={route} className="inline-block mt-1">
            <Component className="w-10 p-1 " isFilled={route === path} />
        </Link>
    );
};

export default NavItem;
