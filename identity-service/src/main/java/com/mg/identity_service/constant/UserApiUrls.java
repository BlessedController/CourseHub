package com.mg.identity_service.constant;

public final class UserApiUrls {
    private UserApiUrls() {
    }

    public static final String USER_API_BASE_URL = "/v1/user";

    public static final String REGISTER_URL = "/register";
    public static final String LOGIN_URL = "/login";
    public static final String ADMIN_ALL_USERS_URL = "/admin/all-users";
    public static final String GET_BY_ID_URL = "/{id}";
    public static final String GET_SELF_URL = "/self";
    public static final String DELETE_BY_ID_URL = "/{id}";
    public static final String UPDATE_USER_URL = "/update-user/{id}";
    public static final String UPDATE_USERNAME_URL = "/update-username/{id}";
    public static final String UPDATE_PASSWORD_URL = "/update-password/{id}";
    public static final String UPDATE_EMAIL_URL = "/update-email/{id}";
    public static final String UPDATE_PHONE_NUMBER_URL = "/update-phone-number/{id}";

}
