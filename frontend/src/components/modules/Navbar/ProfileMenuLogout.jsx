"use client";
import React from "react";
import { DropdownMenuItem } from "@/components/ui/dropdown-menu";
import { signOut } from "next-auth/react";

const ProfileMenuLogout = () => {
    return (
        <DropdownMenuItem className="cursor-pointer" onClick={signOut}>
            Log out
        </DropdownMenuItem>
    );
};

export default ProfileMenuLogout;
