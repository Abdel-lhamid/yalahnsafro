package com.yallahnsafro.yallahnsafrobackend.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 990000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/registration";
    public static final String TOKEN_SECRET = "sdfghjkl95asasasadzdfghj?=8956";
    public static final String REFRESH_TOKEN_HEADER_STRING = "RefreshToken";

    public static final String USER_ROLE_ADMIN = "ADMIN";
    public static final String USER_ROLE_ORGANIZER = "ORGANIZER";
    public static final String USER_ROLE_CUSTOMER = "CUSTOMER";


}
