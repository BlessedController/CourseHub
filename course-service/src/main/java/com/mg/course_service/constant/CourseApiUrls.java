package com.mg.course_service.constant;

public class CourseApiUrls {

    private CourseApiUrls() {
    }

    public static final String COURSE_API_BASE_URL = "/v1/course";

    public static final String CREATE_COURSE_URL = "/create-course";
    public static final String UPDATE_COURSE_URL = "/author/update-course/{courseId}";
    public static final String DELETE_COURSE_URL = "/author/delete-course/{courseId}";
    public static final String ALL_COURSES_URL = "/all-courses";
    public static final String COURSE_BY_ID_URL = "/{id}";
    public static final String AUTHOR_COURSES_URL = "/user-courses";
    public static final String VALIDATE_OWNER_URL = "/validate-owner";
}

