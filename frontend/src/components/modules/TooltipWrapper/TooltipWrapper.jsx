import {
    Tooltip,
    TooltipContent,
    TooltipTrigger,
} from "@/components/ui/tooltip";
import React from "react";

const TooltipWrapper = ({ children, content, side }) => {
    return (
        <Tooltip>
            <TooltipTrigger>{children}</TooltipTrigger>
            <TooltipContent side={side}>
                <p>{content}</p>
            </TooltipContent>
        </Tooltip>
    );
};

export default TooltipWrapper;
