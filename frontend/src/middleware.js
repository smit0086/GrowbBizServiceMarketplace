import { withAuth } from "next-auth/middleware";
import { NextResponse } from "next/server";
import { BUSSINESS_STATUS, ROLES } from "./lib/constants";
import { getBusiness } from "./services/businessService";

// middleware is applied to all routes, use conditionals to select
const protected_routes = {
    CUSTOMER: ["/dashboard"],
    PARTNER: [
        "/partner/dashboard",
        "/partner/business/status",
        "/partner/business/create",
        "/partner/business/service"
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
                    business = await getBusiness(
                        req.nextauth.token.email,
                        req.nextauth.token.apiToken
                    );
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
                    if (business.status !== BUSSINESS_STATUS.APPROVED) {
                        if (
                            req.nextUrl.pathname !== "/partner/business/status"
                        ) {
                            return NextResponse.redirect(
                                new URL("/partner/business/status", req.url)
                            );
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
