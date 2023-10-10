import { withAuth } from "next-auth/middleware";

// middleware is applied to all routes, use conditionals to select
const protected_routes = {
    CUSTOMER: ["/dashboard"],
    PARTNER: ["/partner/dashboard"],
    ADMIN: [["/admin/dashboard"]],
};
const flattened_protected_routes = [
    ...protected_routes.CUSTOMER,
    ...protected_routes.PARTNER,
    ...protected_routes.ADMIN,
];
export default withAuth(function middleware(req) {}, {
    callbacks: {
        authorized: ({ req, token }) => {
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
            if (isVisitingProtectedRoute && !token) {
                return false;
            }
            return true;
        },
    },
});
