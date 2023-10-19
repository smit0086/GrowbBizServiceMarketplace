import { COPY } from "@/lib/constants";
import { Icons } from "../icons";

export const AuthTestimonial = ({ testimonial, name }) => {
    return (
        <div className="relative hidden h-full flex-col bg-muted p-10 text-white dark:border-r lg:flex">
            <div className="absolute inset-0 bg-zinc-900" />
            <div className="relative z-20 flex items-center text-lg font-medium">
                <Icons.logo className="mr-2 h-6 w-6" />
                {COPY.APP_NAME}
            </div>
            <div className="relative z-20 mt-auto">
                <blockquote className="space-y-2">
                    <p className="text-lg">&ldquo;{testimonial}&rdquo;</p>
                    <footer className="text-sm">{name}</footer>
                </blockquote>
            </div>
        </div>
    );
};
