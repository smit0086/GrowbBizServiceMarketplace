import { buttonVariants } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import Link from "next/link";
import { UserAuthForm } from "./components/UserAuthForm";
import { AuthTestimonial } from "@/components/modules/AuthTestimonial";
import { COPY } from "@/lib/constants";

export const metadata = {
    title: `${COPY.APP_NAME} | Customer login`,
};

export default function CustomerLogin() {
    return (
        <div className="container relative h-screen flex-col items-center justify-center grid lg:max-w-none lg:grid-cols-2 lg:px-0">
            <Link
                href="./signup"
                className={cn(
                    buttonVariants({ variant: "ghost" }),
                    "absolute right-4 top-4 md:right-8 md:top-8"
                )}
            >
                Signup
            </Link>
            <AuthTestimonial
                testimonial={COPY.CUSTOMER_TESTIMONIAL}
            />
            <div className="lg:p-8">
                <div className="mx-auto flex w-full flex-col justify-center space-y-6 sm:w-[350px]">
                    <div className="flex flex-col space-y-2 text-center">
                        <h1 className="text-2xl font-semibold tracking-tight">
                            Hello 👋
                        </h1>
                        <p className="text-sm text-muted-foreground">
                            Welcome back!
                        </p>
                    </div>
                    <UserAuthForm />
                    <p className="px-8 text-center text-sm text-muted-foreground" style={{ whiteSpace: 'nowrap' }}>
                        Are you a service partner? please{" "}
                        <Link
                            href="/partner/login"
                            className="underline underline-offset-4 hover:text-primary"
                        >
                            login
                        </Link>{" "}
                        here.
                    </p>
                </div>
            </div>
        </div>
    );
}