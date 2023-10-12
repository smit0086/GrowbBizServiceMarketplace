"use client";
import { Button } from "@/components/ui/button";
import { signOut } from "next-auth/react";
import React from "react";

const Navbar = ({ callbackUrl }) => {
    return (
        <Button
            onClick={() =>
                signOut({
                    callbackUrl: callbackUrl ?? "/login",
                })
            }
        >
            Logout
        </Button>
    );
};

export default Navbar;
