package com.KayraAtalay.config;

public class RestApis {

    public static final String DEVELOPER = "/dev";
    public static final String VERSIONS = "/v1";
    public static final String AUTH = DEVELOPER + VERSIONS + "/logi-track-auth";

    public static final String REGISTER = "/register";
    public static final String AUTHENTICATE = "/authenticate";
    public static final String REFRESH_TOKEN = "/refresh-token";
    public static final String FIND_USER_ID_BY_USERNAME = "/find-user-id-by-username";
}
