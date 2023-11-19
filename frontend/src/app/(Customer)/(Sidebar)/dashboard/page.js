import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { Icons } from "@/components/icons";
import { Card } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { getAllCategories } from "@/services/categoryService";
import { getServerSession } from "next-auth";
import React from "react";

const IconManager = ({ name }) => {
    if (name.trim().toLowerCase() === "beauty & aesthetics") {
        return <Icons.beauty width="100%" height="100%" />;
    } else if (name.trim().toLowerCase() === "house services") {
        return <Icons.housing width="100%" height="100%" />;
    } else if (name.trim().toLowerCase() === "care taker") {
        return <Icons.caretaker width="100%" height="100%" />;
    } else if (name.trim().toLowerCase() === "tutoring") {
        return <Icons.tutoring width="100%" height="100%" />;
    }
    return (
        <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            className="mb-3 h-8 w-8"
            width="100%"
            height="100%"
        >
            <rect width="20" height="14" x="2" y="5" rx="2" />
            <path d="M2 10h20" />
        </svg>
    );
};

const CategoryCard = ({ category }) => {
    return (
        <a href={`/dashboard/${category.categoryID}`}>
            <Card>
                <Label
                    htmlFor="card"
                    className="cursor-pointer flex flex-col items-center justify-between rounded-md border-2 border-muted bg-transparent p-16 hover:bg-accent hover:text-accent-foreground peer-data-[state=checked]:border-primary [&:has([data-state=checked])]:border-primary"
                >
                    <div className="w-[50px] h-[50px]">
                        <IconManager name={category.name} />
                    </div>
                    {category.name}
                </Label>
            </Card>
        </a>
    );
};

const CustomerDashboard = async () => {
    const session = await getServerSession(authOptions);
    const categories = await getAllCategories(session.apiToken);
    return (
        <div className="p-8 pl-16">
            <h1 className="text-4xl font-semibold tracking-tight">Hello 👋</h1>
            <h2 className="text-2xl tracking-tight mb-8">
                What service are you looking for today?
            </h2>
            <div className="grid grid-cols-2 gap-4">
                {categories.map((category) => {
                    return (
                        <CategoryCard
                            key={category.categoryID}
                            category={category}
                        />
                    );
                })}
            </div>
        </div>
    );
};

export default CustomerDashboard;
