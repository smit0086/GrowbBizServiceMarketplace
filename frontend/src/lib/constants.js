export const COPY = {
    APP_NAME: "GrowBiz",
    APP_TAGLINE: "Empowering Local Dreams, Growing Local Businesses.",
    PARTNER_TESTIMONIAL:
        "GrowBiz works wonders! Our sales have soared since using it. Highly recommended!",
    PARTNER_NAME: "John Doe",
    CUSTOMER_TESTIMONIAL:
        "Your trusted partner for on-demand home services, just a click away.",
};

export const REGEX = {
    EMAIL: /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/,
    PASSWORD_POLICY: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,}$/,
};

export const ERROR_MESSAGE = {
    REQUIRED: "This field is required",
    INVALID_EMAIL: "Please enter a valid email",
    INVALID_PASSWORD:
        "Password should be 8 characters and include at least 1 letter, 1 number and 1 special character!",
    PASSWORD_NOT_MATCH: "Password do not match!",
    INVALID_IMAGE_FORMAT: "Please upload a image having type (png, jpeg or jpg).",
    INVALID_SERVICE_REQUIRED_TIME: "Time required should be a multiple of 30 minutes."
};

export const ROLES = {
    ADMIN: "ADMIN",
    PARTNER: "PARTNER",
    CUSTOMER: "CUSTOMER",
};

export const BUSSINESS_STATUS = {
    APPROVED: "APPROVED",
    DECLINED: "DECLINED",
    PENDING: "PENDING",
};

export const DAY_OF_WEEK = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
];

export const BOOKING_STATUS = {
    UPCOMING: "UPCOMING",
    ONGOING: "ONGOING",
    COMPLETED: "COMPLETED"
}
export const ALLOWED_IMAGE_TYPES = [
    'image/jpeg',
    'image/png',
    'image/jpg'
];
