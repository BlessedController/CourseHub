package com.mg.api_gateway.constant;

public class ServiceUrlsForSecurity {
    public static final String USER_LOGIN_URL = "/v1/user/login";
    public static final String USER_REGISTER_URL = "/v1/user/register";
    public static final String COURSE_API_ALL_COURSES = "/v1/course/all-courses";
    public static final String SWAGGER_UI_URL = "/swagger-ui/**";
    public static final String SWAGGER_API_DOCS_URL = "/v3/api-docs/**";
    public static final String SWAGGER_WEB_JARS_URL = "/webjars/**";


    public static final String COURSE_API_AUTHOR_URLS = "/v1/course/author/**";
    public static final String VIDEO_API_AUTHOR_URLS = "/v1/video/author/**";
    public static final String MEDIA_URL = "/v1/media/**";
    public static final String USER_ADMIN_URLS = "/v1/user/admin/**";

    public static final String USER_USER_URLS = "/v1/user/**";
    public static final String COURSE_USER_URLS = "/v1/course/**";
}
