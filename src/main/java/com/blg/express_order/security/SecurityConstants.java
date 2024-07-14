package com.blg.express_order.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class SecurityConstants {
    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final byte[] SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

    public static final long EXPIRATION_TIME = 600_000;// 10 min


    public static final String TOKEN_PREFIX = "";

}
