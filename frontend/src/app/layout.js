import { getServerSession } from "next-auth/next";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { COPY } from "@/lib/constants";
import "./globals.css";
import { Inter } from "next/font/google";
import SessionProvider from "./context/SessionProvider";
import { Toaster } from "@/components/ui/toaster";

const inter = Inter({ subsets: ["latin"] });

export const metadata = {
    title: COPY.APP_NAME,
    description: COPY.APP_TAGLINE,
};

export default async function RootLayout({ children }) {
    const session = await getServerSession(authOptions);
    return (
        <html lang="en">
            <body className={inter.className}>
                <SessionProvider session={session}>{children}</SessionProvider>
                <Toaster />
            </body>
        </html>
    );
}
