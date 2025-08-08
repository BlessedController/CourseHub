package com.mg.api_gateway.constant;

public final class ServiceUrlsForGatewayConfig {
    private ServiceUrlsForGatewayConfig() {}

    public static final String IDENTITY_ID = "identity";
    public static final String COURSE_ID = "course";
    public static final String MEDIA_ID = "media";

    public static final String IDENTITY_PATH = "/v1/user/**";
    public static final String COURSE_PATH = "/v1/course/**";
    public static final String MEDIA_PATH = "/v1/media/**";

    public static final String IDENTITY_LB_URI = "lb://identity-service";
    public static final String COURSE_LB_URI = "lb://course-service";
    public static final String MEDIA_LB_URI = "lb://media-stock-service";


}
