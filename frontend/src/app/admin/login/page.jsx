import { buttonVariants } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import Link from "next/link";
import { UserAuthForm } from "./components/UserAuthForm";
import { AuthTestimonial } from "@/components/modules/AuthTestimonial";
import { COPY } from "@/lib/constants";

export const metadata = {
    title: `${COPY.APP_NAME} | Admin login`,
};

export default function ProviderLogin() {
    return (
        <div className="lg:p-8">
            <div className="mx-auto flex w-full flex-col justify-center space-y-6 sm:w-[350px]">
                <div className="flex flex-col space-y-2 text-center">
                    <h1 className="text-2xl font-semibold tracking-tight">
                        Hello Admin 👋
                    </h1>
                </div>
                <UserAuthForm />
            </div>
        </div>
    );
}
