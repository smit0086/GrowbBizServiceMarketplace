"use client";
import { Icons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
import { Switch } from "@/components/ui/switch";
import { useToast } from "@/components/ui/use-toast";
import { DAY_OF_WEEK } from "@/lib/constants";
import { saveBusinessHours } from "@/services/businessService";
import { useSession } from "next-auth/react";
import React, { useState } from "react";

const getMinutesToTuple = (minutes) => {
    const hour = minutes / 60;
    const min = minutes % 60;
    return [hour, min];
};

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

const generateTimeStringIn24Hr = (minutes) => {
    let hour = addPadding(Math.floor(minutes / 60).toString());
    const min = addPadding((minutes % 60).toString());
    return `${hour}:${min}`;
};
const convertStateToRequestBody = (state) => {
    const request = {};
    Object.keys(state).forEach((key) => {
        if (state[key]) {
            request[key.toUpperCase()] = [
                generateTimeStringIn24Hr(state[key].start),
                generateTimeStringIn24Hr(state[key].end),
            ];
        }
    });
    return request;
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

const OperatingHoursForm = ({ businessHours, businessId }) => {
    const [operatingHours, setOperatingHours] = useState(businessHours);
    const [isSaving, setIsSaving] = useState(false);
    const session = useSession();
    const { toast } = useToast();
    const onSubmit = () => {
        setIsSaving(true);
        const requestBody = convertStateToRequestBody(operatingHours);
        const response = saveBusinessHours(
            session.data.apiToken,
            businessId,
            requestBody
        );
        if (response) {
            toast({
                title: "Business operating hours saved",
            });
        }
        setIsSaving(false);
    };
    const onChange = (key, secondaryKey, value) => {
        setOperatingHours((prev) => {
            const _operatingHours = JSON.parse(JSON.stringify(prev));
            _operatingHours[key][secondaryKey] = parseInt(value);
            return _operatingHours;
        });
    };
    const onSelectChange = (key, value) => {
        setOperatingHours((prev) => {
            const _operatingHours = JSON.parse(JSON.stringify(prev));
            if (value) {
                _operatingHours[key] = {
                    start: 540,
                    end: 1020,
                };
            } else {
                _operatingHours[key] = null;
            }
            return _operatingHours;
        });
    };
    return (
        <Card className="max-w-[1000px] ml-16">
            <CardContent className="pt-6 pb-0">
                {DAY_OF_WEEK.map((item) => (
                    <div className="flex items-center mb-4" key={item}>
                        <div className="flex items-center w-[130px] h-[36px]">
                            <Switch
                                id={item.toLowerCase()}
                                className="mr-2"
                                checked={!!operatingHours[item]}
                                onCheckedChange={(value) =>
                                    onSelectChange(item, value)
                                }
                            />
                            <Label htmlFor={item.toLowerCase()}>{item}</Label>
                        </div>
                        {operatingHours[item] && (
                            <div className="ml-4 flex">
                                <Select
                                    value={operatingHours[
                                        item
                                    ].start.toString()}
                                    onValueChange={(value) =>
                                        onChange(item, "start", value)
                                    }
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
                                                    key={`start_${time}`}
                                                >
                                                    {generateTimeString(time)}
                                                </SelectItem>
                                            ))}
                                        </SelectGroup>
                                    </SelectContent>
                                </Select>
                                <span className="mx-3">-</span>
                                <Select
                                    value={operatingHours[item].end.toString()}
                                    onValueChange={(value) =>
                                        onChange(item, "end", value)
                                    }
                                >
                                    <SelectTrigger className="w-[150px]">
                                        <SelectValue placeholder="Select" />
                                    </SelectTrigger>
                                    <SelectContent className="max-h-[300px] ">
                                        <SelectGroup>
                                            {TIME_LIST.filter(
                                                (time) =>
                                                    time >
                                                    operatingHours[item].start
                                            ).map((time) => (
                                                <SelectItem
                                                    key={`end_${time}`}
                                                    value={time.toString()}
                                                >
                                                    {generateTimeString(time)}
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
                <Button onClick={onSubmit} disabled={isSaving}>
                    {isSaving && (
                        <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                    )}
                    Save
                </Button>
            </CardFooter>
        </Card>
    );
};

export default OperatingHoursForm;
