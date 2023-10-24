"use client";
import { Button } from "@/components/ui/button";
import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import { Switch } from "@/components/ui/switch";
import React, { useState } from "react";

const dayOfWeek = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
];

const addPadding = (str) => {
    if (str.length === 1) {
        return `0${str}`;
    }
    return str;
};

const generateTimeString = (minutes) => {
    const AM_PM_MARK = 720;
    let hour = addPadding(Math.floor((minutes / 60) % 12).toString());
    const min = addPadding((minutes % 60).toString());
    const am_pm = minutes < AM_PM_MARK ? "am" : "pm";
    hour = hour === "00" ? "12" : hour;
    return `${hour}:${min}${am_pm}`;
};

const generateTimeList = () => {
    const DIVISIONS = 96;
    const time = [];
    for (let i = 0; i < DIVISIONS; i++) {
        let minutes = i * 15;
        time.push(minutes);
    }
    time.push(1439);
    return time;
};

const TIME_LIST = generateTimeList();
const INITAL_STATE = {
    [dayOfWeek[0]]: null,
    [dayOfWeek[1]]: {
        start: 540,
        end: 1020,
    },
    [dayOfWeek[2]]: {
        start: 540,
        end: 1020,
    },
    [dayOfWeek[3]]: {
        start: 540,
        end: 1020,
    },
    [dayOfWeek[4]]: {
        start: 540,
        end: 1020,
    },
    [dayOfWeek[5]]: {
        start: 540,
        end: 1020,
    },
    [dayOfWeek[6]]: null,
};

const OperatingHours = () => {
    const [operatingHours, setOperatingHours] = useState(INITAL_STATE);
    return (
        <div>
            <h2 className="text-3xl p-8 pl-16">Operating hours</h2>
            <Card className="max-w-[1000px] ml-16">
                <CardContent className="pt-6 pb-0">
                    {dayOfWeek.map((item) => (
                        <div className="flex items-center mb-6" key={item}>
                            <div className="flex items-center w-[130px]">
                                <Switch
                                    id={item.toLowerCase()}
                                    className="mr-2"
                                    checked={!!operatingHours[item]}
                                />
                                <Label htmlFor={item.toLowerCase()}>
                                    {item}
                                </Label>
                            </div>
                            {operatingHours[item] && (
                                <div className="ml-4 flex">
                                    <Select
                                        value={operatingHours[
                                            item
                                        ].start.toString()}
                                    >
                                        <SelectTrigger className="w-[150px]">
                                            <SelectValue placeholder="Select" />
                                        </SelectTrigger>
                                        <SelectContent className="max-h-[300px] ">
                                            <SelectGroup>
                                                {TIME_LIST.filter(
                                                    (time) =>
                                                        time <
                                                        operatingHours[item].end
                                                ).map((time) => (
                                                    <SelectItem
                                                        value={time.toString()}
                                                    >
                                                        {generateTimeString(
                                                            time
                                                        )}
                                                    </SelectItem>
                                                ))}
                                            </SelectGroup>
                                        </SelectContent>
                                    </Select>
                                    <span className="mx-3">-</span>
                                    <Select
                                        value={operatingHours[
                                            item
                                        ].end.toString()}
                                    >
                                        <SelectTrigger className="w-[150px]">
                                            <SelectValue placeholder="Select" />
                                        </SelectTrigger>
                                        <SelectContent className="max-h-[300px] ">
                                            <SelectGroup>
                                                {TIME_LIST.filter(
                                                    (time) =>
                                                        time >
                                                        operatingHours[item]
                                                            .start
                                                ).map((time) => (
                                                    <SelectItem
                                                        value={time.toString()}
                                                    >
                                                        {generateTimeString(
                                                            time
                                                        )}
                                                    </SelectItem>
                                                ))}
                                            </SelectGroup>
                                        </SelectContent>
                                    </Select>
                                </div>
                            )}
                        </div>
                    ))}
                </CardContent>
                <CardFooter className="flex justify-between">
                    <Button>Save</Button>
                </CardFooter>
            </Card>
        </div>
    );
};

export default OperatingHours;
