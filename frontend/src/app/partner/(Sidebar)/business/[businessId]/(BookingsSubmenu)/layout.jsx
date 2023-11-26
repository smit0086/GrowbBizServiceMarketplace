import SubmenuNavItem from "@/components/modules/Navbar/Submenu";
import React from "react";

const BookingSubmenuLayout = ({ children }) => {
    return (
        <div className="relative flex">
            <div className="h-screen min-w-[250px] bg-slate-50">
                <h2 className="text-4xl font-semibold tracking-tight p-4">
                    Bookings
                </h2>
                <ul className="p-4">
                    <li className="my-2">
                        <SubmenuNavItem
                            link="./upcoming-bookings"
                            title={"Upcoming"}
                        />
                    </li>
                    <li className="my-2">
                        <SubmenuNavItem
                            link="./ongoing-bookings"
                            title={"Ongoing"}
                        />
                        {/* <a href="./ongoing-bookings">Ongoing</a> */}
                    </li>
                    <li className="my-2">
                        <SubmenuNavItem link="./past-bookings" title={"Past"} />
                        {/* <a href="./past-bookings">Past</a> */}
                    </li>
                </ul>
            </div>
            <div className="grow" style={{ width: `calc(100% - 250px)` }}>
                {children}
            </div>
        </div>
    );
};

export default BookingSubmenuLayout;
