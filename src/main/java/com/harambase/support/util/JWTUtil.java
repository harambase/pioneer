package com.harambase.support.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;

public class JWTUtil {

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param user 用户
     * @param secret   用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, LinkedHashMap user, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", (String) user.get("username"))
                    .withClaim("userId", (String) user.get("userId"))
                    .withClaim("firstName", (String) user.get("firstName"))
                    .withClaim("lastName", (String) user.get("lastName"))
                    .withClaim("status", (String) user.get("status"))
                    .withClaim("info", (String) user.get("info"))
                    .withClaim("type", (String) user.get("type"))
                    .withClaim("roleId", (String) user.get("roleId"))
                    .withClaim("birthday", (String) user.get("birthday"))
                    .withClaim("email", (String) user.get("email"))
                    .withClaim("tel", (String) user.get("tel"))
                    .withClaim("qq", (String) user.get("qq"))
                    .withClaim("weChat", (String) user.get("weChat"))
                    .withClaim("dorm", (String) user.get("dorm"))
                    .withClaim("gender", (String) user.get("gender"))
                    .withClaim("createTime", (String) user.get("createTime"))
                    .withClaim("updateTime", (String) user.get("updateTime"))
                    .withClaim("baseInfo", (String) user.get("baseInfo"))
                    .withClaim("comment", (String) user.get("comment"))
                    .withClaim("profile", (String) user.get("profile"))
                    .withClaim("userInfo", (String) user.get("userInfo"))
                    .withClaim("address", (String) user.get("address"))
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String sign(LinkedHashMap user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256((String) user.get("password"));

            String token = JWT.create()
                    .withClaim("username", (String) user.get("username"))
                    .withClaim("userId", (String) user.get("userId"))
                    .withClaim("firstName", (String) user.get("firstName"))
                    .withClaim("lastName", (String) user.get("lastName"))
                    .withClaim("status", (String) user.get("status"))
                    .withClaim("info", (String) user.get("info"))
                    .withClaim("type", (String) user.get("type"))
                    .withClaim("roleId", (String) user.get("roleId"))
                    .withClaim("birthday", (String) user.get("birthday"))
                    .withClaim("email", (String) user.get("email"))
                    .withClaim("tel", (String) user.get("tel"))
                    .withClaim("qq", (String) user.get("qq"))
                    .withClaim("weChat", (String) user.get("weChat"))
                    .withClaim("dorm", (String) user.get("dorm"))
                    .withClaim("gender", (String) user.get("gender"))
                    .withClaim("createTime", (String) user.get("createTime"))
                    .withClaim("updateTime", (String) user.get("updateTime"))
                    .withClaim("baseInfo", (String) user.get("baseInfo"))
                    .withClaim("comment", (String) user.get("comment"))
                    .withClaim("profile", (String) user.get("profile"))
                    .withClaim("userInfo", (String) user.get("userInfo"))
                    .withClaim("address", (String) user.get("address"))
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}

