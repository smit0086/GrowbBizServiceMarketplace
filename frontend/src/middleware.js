import { withAuth } from "next-auth/middleware";

// middleware is applied to all routes, use conditionals to select
const proected_routes = ["/dashboard", "/partner/dashboard"];
export default withAuth(function middleware(req) {}, {
    callbacks: {
        authorized: ({ req, token }) => {
            console.log(token);
            const isVisitingProtectedRoute = proected_routes.reduce(
                (prev, curr) => {
                    return (
                        prev ||
                        req.nextUrl.pathname === "/" ||
                        req.nextUrl.pathname.startsWith(curr)
                    );
                },
                false
            );
            if (isVisitingProtectedRoute && token === null) {
                return false;
            }
            return true;
        },
    },
});
