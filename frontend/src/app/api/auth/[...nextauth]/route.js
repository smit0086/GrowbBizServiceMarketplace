import NextAuth from "next-auth";
import { authenticate } from "@/services/authService";
import CredentialsProvider from "next-auth/providers/credentials";

export const authOptions = {
    providers: [
        CredentialsProvider({
            name: "Credentials",
            credentials: {
                email: { label: "Email", type: "text" },
                password: { label: "Password", type: "password" },
                role: { label: "Role", type: "role" },
            },
            async authorize(credentials, req) {
                if (typeof credentials !== "undefined") {
                    const res = await authenticate(
                        credentials.email,
                        credentials.password,
                        credentials.role
                    );
                    if (res.ok) {
                        return {
                            email: res.subject,
                            role: res.role,
                            apiToken: res.token,
                        };
                    } else {
                        console.log("Invalid credentials");
                        return null;
                    }
                } else {
                    console.log("Invalid credentials !!");
                    return null;
                }
            },
        }),
    ],
    session: { strategy: "jwt", maxAge: 60 * 60 * 24 },
    pages: {
        signIn: "/login",
        signOut: "logout",
        newUser: "/signup", // New users will be directed here on first sign in (leave the property out if not of interest)
    },
    callbacks: {
        async session({ session, token, user }) {
            const sanitizedToken = Object.keys(token).reduce((p, c) => {
                // strip unnecessary properties
                if (
                    c !== "iat" &&
                    c !== "exp" &&
                    c !== "jti" &&
                    c !== "apiToken"
                ) {
                    return { ...p, [c]: token[c] };
                } else {
                    return p;
                }
            }, {});
            return {
                ...session,
                user: sanitizedToken,
                apiToken: token.apiToken,
            };
        },
        async jwt({ token, user, account, profile }) {
            if (typeof user !== "undefined") {
                return user;
            }
            return token;
        },
    },
};

const handler = NextAuth(authOptions);

export { handler as GET, handler as POST };
