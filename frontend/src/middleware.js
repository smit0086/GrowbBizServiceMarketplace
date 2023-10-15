import { withAuth } from "next-auth/middleware";
import { NextResponse } from "next/server";
import { ROLES } from "./lib/constants";

// middleware is applied to all routes, use conditionals to select
const protected_routes = {
    CUSTOMER: ["/dashboard"],
    PARTNER: [
        "/partner/dashboard",
        "/partner/business/status",
        "/partner/business/create",
    ],
    ADMIN: ["/admin/dashboard", "/admin/business/verify"],
};

const flattened_protected_routes = [
    ...protected_routes.CUSTOMER,
    ...protected_routes.PARTNER,
    ...protected_routes.ADMIN,
];
export default withAuth(
    async function middleware(req) {
        if (req.nextUrl.pathname.startsWith("/api")) {
            return;
        }
        if (req.nextauth?.token?.role) {
            const isVisitingProtectedRoute = flattened_protected_routes.reduce(
                (prev, curr) => {
                    return (
                        prev ||
                        req.nextUrl.pathname === "/" ||
                        req.nextUrl.pathname.startsWith(curr)
                    );
                },
                false
            );
            const isPageAllowedForRole = protected_routes[
                req.nextauth.token.role
            ].reduce((prev, curr) => {
                return (
                    prev ||
                    req.nextUrl.pathname === "/" ||
                    req.nextUrl.pathname.startsWith(curr)
                );
            }, false);
            if (req.nextauth?.token?.role === ROLES.PARTNER) {
                let business = [];
                let businessLength = 0;
                try {
                    business = await (
                        await fetch(
                            `${process.env.SERVER_ADDRESS}/business/?email=${req.nextauth.token.email}`,
                            {
                                method: "get",
                                headers: {
                                    "Content-type": "application/json",
                                    Authorization: `Bearer ${req.nextauth.token.apiToken}`,
                                },
                            }
                        )
                    ).json();
                    businessLength = business?.businesses?.length;
                    business = business?.businesses[0];
                } catch (err) {
                    console.error(err);
                }
                if (!businessLength) {
                    if (req.nextUrl.pathname !== "/partner/business/create") {
                        return NextResponse.redirect(
                            new URL("/partner/business/create", req.url)
                        );
                    }
                } else {
                    if (business.status !== "APPROVED") {
                        console.log(
                            "req.nextUrl.pathname",
                            req.nextUrl.pathname
                        );
                        if (
                            req.nextUrl.pathname !== "/partner/business/status"
                        ) {
                            console.log(
                                "req.nextUrl.pathname",
                                req.nextUrl.pathname
                            );
                            // return NextResponse.redirect(
                            //     new URL("/partner/business/status", req.url)
                            // );
                        }
                    }
                    if (req.nextUrl.pathname === "/partner/business/create") {
                        return NextResponse.redirect(new URL("/", req.url));
                    }
                }
            }
            if (!isVisitingProtectedRoute || !isPageAllowedForRole) {
                return NextResponse.redirect(new URL("/", req.url));
            }
        }
    },
    {
        callbacks: {
            authorized: ({ req, token }) => {
                if (req.nextUrl.pathname.startsWith("/api")) {
                    return true;
                }
                const isVisitingProtectedRoute =
                    flattened_protected_routes.reduce((prev, curr) => {
                        return (
                            prev ||
                            req.nextUrl.pathname === "/" ||
                            req.nextUrl.pathname.startsWith(curr)
                        );
                    }, false);
                if (isVisitingProtectedRoute && !token) {
                    return false;
                }
                return true;
            },
        },
    }
);
