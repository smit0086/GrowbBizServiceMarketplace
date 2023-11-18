import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { Icons } from "@/components/icons";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { getBusiness } from "@/services/businessService";
import { getServerSession } from "next-auth";
import React from "react";
import NavItem from "./NavItem";
import ProfileMenuLogout from "./ProfileMenuLogout";
import { ROLES } from "@/lib/constants";

const Navbar = async ({ navItems }) => {
    const session = await getServerSession(authOptions);
    console.log({ session });
    let businessName = session.user.role;
    if (businessName === ROLES.PARTNER) {
        const business = (
            await getBusiness(session.user.email, session.apiToken)
        )?.businesses?.[0];
        businessName = business?.businessName;
    }

    return (
        <div className="absolute p-3 z-50 bg-secondary flex flex-col w-16 h-screen justify-between items-center">
            <a href={`/${session.user.role.toLowerCase()}/dashboard`}>
                <Icons.logo className="w-full p-1" />
            </a>
            <div className="h-full mt-24">
                {navItems?.map((item) => (
                    <NavItem
                        key={item.title}
                        route={item.route}
                        icon={item.icon}
                        iconClassNames={item.iconClassNames}
                    />
                ))}
            </div>
            <DropdownMenu>
                <DropdownMenuTrigger asChild>
                    <Button
                        variant="ghost"
                        className="relative h-8 w-8 rounded-full"
                    >
                        <Avatar>
                            <AvatarImage
                                src={`${process.env.NEXT_PUBLIC_CDN_ADDRESS}avatar.svg`}
                            />
                            <AvatarFallback>AV</AvatarFallback>
                        </Avatar>
                    </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent className="w-56" align="end" forceMount>
                    <DropdownMenuLabel className="font-normal">
                        <div className="flex flex-col space-y-1">
                            <p className="text-sm font-medium leading-none">
                                {businessName}
                            </p>
                            <p className="text-xs leading-none text-muted-foreground">
                                {session.user.email}
                            </p>
                        </div>
                    </DropdownMenuLabel>
                    <DropdownMenuSeparator />
                    {businessName === ROLES.CUSTOMER && (
                        <>
                            <DropdownMenuItem>
                                <a href="/payment">Payments</a>
                            </DropdownMenuItem>
                            <DropdownMenuSeparator />
                        </>
                    )}
                    <ProfileMenuLogout />
                </DropdownMenuContent>
            </DropdownMenu>
        </div>
    );
};

export default Navbar;
