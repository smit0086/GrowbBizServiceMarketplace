import { StatusVerification } from "./components/StatusVerification";
import { buttonVariants } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import Link from "next/link";

export default function ProviderBusinessStatus() {
    return (
        <>
            <Link
                href="/partner/login"
                className={cn(
                    buttonVariants({ variant: "ghost" }),
                    "absolute right-4 top-4 md:right-8 md:top-8"
                )}
            >
                Logout
            </Link>
            <div style={centeringStyles}>
                <StatusVerification />
            </div>
        </>
    );
}

const centeringStyles = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    height: '50vh'
};