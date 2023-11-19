"use client";
import React, { useRef, useState } from "react";
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { DropdownMenuSeparator } from "@/components/ui/dropdown-menu";
import { Slider } from "@/components/ui/slider";
import { AspectRatio } from "@/components/ui/aspect-ratio";
import Image from "next/image";

const getServicesBySubcategory = (services, subcategoryId) => {
    return services.filter(
        (service) => service.subCategoryId === subcategoryId
    );
};

const ServiceCard = (props) => {
    const service = props.service;
    console.log({ service });
    return (
        <a href={`/service/${service.serviceId}/booking`}>
            <Card className="w-[300px] mr-4 mb-4 cursor-pointer hover:bg-accent hover:text-accent-foreground">
                <CardHeader className="space-y-1">
                    <AspectRatio
                        ratio={1 / 1}
                        className="overflow-hidden rounded mb-2"
                    >
                        {service.imageURL && (
                            <Image
                                src={service.imageURL}
                                className="object-cover max-w-[250px] max-h-[250px]"
                                width={250}
                                height={250}
                            />
                        )}
                    </AspectRatio>
                    <CardTitle>{service.businessName}</CardTitle>
                    <CardDescription>{service.description}</CardDescription>
                </CardHeader>
                <CardContent>
                    <div className="grid w-full items-center gap-4">
                        <div className="flex flex-col space-y-1.5">
                            <Label
                                htmlFor={`service-${service.serviceId}`}
                                style={{ fontSize: "1rem", fontWeight: "bold" }}
                            >
                                Price
                            </Label>
                            <span
                                id={`service-${service.serviceId}`}
                                style={{ fontSize: "0.875rem" }}
                            >
                                {service.price} CAD
                            </span>
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label
                                htmlFor={`service-${service.serviceId}`}
                                style={{ fontSize: "1rem", fontWeight: "bold" }}
                            >
                                Time required
                            </Label>
                            <span
                                id={`service-${service.serviceId}`}
                                style={{ fontSize: "0.875rem" }}
                            >
                                {service.timeRequired[0] * 60 +
                                    service.timeRequired[1]}{" "}
                                minutes
                            </span>
                        </div>
                    </div>
                </CardContent>
            </Card>
        </a>
    );
};

const ServiceList = ({ subcategories, services }) => {
    const [search, setSearch] = useState("");
    const [selectAll, setSelectAll] = useState(true);
    const [categorySelectionMap, setCategorySelectionMap] = useState(() => {
        return subcategories.reduce((map, subcategory) => {
            map[subcategory.subCategoryID] = true;
            return map;
        }, {});
    });
    const onSelectAllChange = (changedValue) => {
        if (!changedValue) {
            setCategorySelectionMap(
                subcategories.reduce((map, subcategory) => {
                    map[subcategory.subCategoryID] = false;
                    return map;
                }, {})
            );
        } else {
            setCategorySelectionMap(
                subcategories.reduce((map, subcategory) => {
                    map[subcategory.subCategoryID] = true;
                    return map;
                }, {})
            );
        }
        setSelectAll(changedValue);
    };
    const onCategorySelectionChange = (subcategoryID, changedValue) => {
        setCategorySelectionMap((prevMap) => {
            return {
                ...prevMap,
                [subcategoryID]: changedValue,
            };
        });
        if (!changedValue) {
            setSelectAll(false);
        }
    };
    const maxPriceRef = useRef(-1);
    const minPriceRef = useRef(Number.MAX_SAFE_INTEGER);
    const maxTimeRef = useRef(-1);
    const minTimeRef = useRef(Number.MAX_SAFE_INTEGER);
    for (let i = 0; i < services.length; i++) {
        if (services[i].price > maxPriceRef.current) {
            maxPriceRef.current = services[i].price;
        }
        if (services[i].price < minPriceRef.current) {
            minPriceRef.current = services[i].price;
        }
        if (
            services[i].timeRequired[0] * 60 + services[i].timeRequired[1] >
            maxTimeRef.current
        ) {
            maxTimeRef.current =
                services[i].timeRequired[0] * 60 + services[i].timeRequired[1];
        }
        if (
            services[i].timeRequired[0] * 60 + services[i].timeRequired[1] <
            minTimeRef.current
        ) {
            minTimeRef.current =
                services[i].timeRequired[0] * 60 + services[i].timeRequired[1];
        }
    }
    const [priceSlider, setPriceSlider] = useState(maxPriceRef.current);
    const [timeSlider, setTimeSlider] = useState(maxTimeRef.current);
    const filteredCategories = subcategories.filter(
        (subcategory) => categorySelectionMap[subcategory.subCategoryID]
    );
    const priceFilterApplied = services.filter(
        (service) => service.price <= priceSlider
    );
    const timeFilterApplied = priceFilterApplied.filter(
        (service) =>
            service.timeRequired[0] * 60 + service.timeRequired[1] <= timeSlider
    );
    const filteredServices = timeFilterApplied.filter((service) => {
        if (search === "") return true;
        return service.businessName
            .toLowerCase()
            .includes(search.toLowerCase());
    });
    return (
        <div>
            <div className="mb-6 max-w-[600px]">
                <Input
                    placeholder="Search business..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                />
            </div>
            <div className="flex">
                <div className="min-w-[285px] mr-4">
                    <Card className="p-3">
                        <div className="my-4">
                            <CardTitle className="mb-2">
                                Sub Categories
                            </CardTitle>
                            <Checkbox
                                checked={selectAll}
                                onCheckedChange={(checked) =>
                                    onSelectAllChange(checked)
                                }
                                id="select_all"
                            />
                            <label htmlFor="select_all" className="pl-1">
                                Select All
                            </label>
                            {subcategories.map((subcategory) => {
                                return (
                                    <div>
                                        <Checkbox
                                            checked={
                                                categorySelectionMap[
                                                    subcategory.subCategoryID
                                                ]
                                            }
                                            onCheckedChange={(checked) => {
                                                onCategorySelectionChange(
                                                    subcategory.subCategoryID,
                                                    checked
                                                );
                                            }}
                                            id={`subcategory-${subcategory.subCategoryID}`}
                                        />
                                        <label
                                            htmlFor={`subcategory-${subcategory.subCategoryID}`}
                                            className="pl-1"
                                        >
                                            {subcategory.name}
                                        </label>
                                    </div>
                                );
                            })}
                        </div>
                        <div className="my-4">
                            <CardTitle className="mb-2">
                                Price : {priceSlider}
                            </CardTitle>
                            <Slider
                                value={[priceSlider]}
                                min={minPriceRef.current}
                                max={maxPriceRef.current}
                                step={1}
                                onValueChange={(value) =>
                                    setPriceSlider(value[0])
                                }
                            />
                        </div>
                        <div className="my-4">
                            <CardTitle className="mb-2">
                                Time required : {timeSlider}
                            </CardTitle>
                            <Slider
                                value={[timeSlider]}
                                min={minTimeRef.current}
                                max={maxTimeRef.current}
                                step={1}
                                onValueChange={(value) =>
                                    setTimeSlider(value[0])
                                }
                            />
                        </div>
                    </Card>
                </div>
                <div>
                    <div>
                        {filteredCategories.map((subcategory) => {
                            return (
                                <div
                                    key={subcategory.subCategoryID}
                                    className="mb-8"
                                >
                                    <h2 className="text-2xl tracking-tight mb-4">
                                        {subcategory.name}
                                    </h2>
                                    <div className="flex flex-wrap">
                                        {getServicesBySubcategory(
                                            filteredServices,
                                            subcategory.subCategoryID
                                        ).map((service) => (
                                            <ServiceCard
                                                service={service}
                                                subCategory={subcategory}
                                            />
                                        ))}
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ServiceList;
