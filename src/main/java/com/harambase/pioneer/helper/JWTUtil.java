package com.harambase.pioneer.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.harambase.pioneer.server.pojo.base.Person;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class JWTUtil {

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param user   用户
     * @param secret 用户的密码
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
                    .withClaim("profile", (String) user.get("profile"))
                    .withClaim("userInfo", (String) user.get("userInfo"))
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String sign(Person user, long nowMillis) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(String.valueOf(nowMillis));

            Calendar now = Calendar.getInstance();
            now.set(Calendar.MINUTE, 60);

            Date issuedAt = new Date(nowMillis);
            Date expiresAt = new Date(now.getTimeInMillis());

            return JWT.create()
                    .withExpiresAt(expiresAt)
                    .withIssuedAt(issuedAt)
                    .withIssuer(user.getUserId())
                    .withClaim("username", user.getUsername())
                    .withClaim("userId", user.getUserId())
                    .withClaim("firstName", user.getFirstName())
                    .withClaim("lastName", user.getLastName())
                    .withClaim("status", user.getStatus())
                    .withClaim("info", user.getInfo())
                    .withClaim("type", user.getType())
                    .withClaim("roleId", user.getRoleId())
                    .withClaim("profile", user.getProfile())
                    .withClaim("userInfo", user.getUserInfo())
                    .sign(algorithm);

        } catch (UnsupportedEncodingException e) {
            return null;
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
                    .withClaim("profile", (String) user.get("profile"))
                    .withClaim("userInfo", (String) user.get("userInfo"))
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
