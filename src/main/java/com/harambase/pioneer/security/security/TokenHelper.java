package com.harambase.pioneer.security.security;

import com.harambase.pioneer.common.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenHelper {

    //expires_in: 3600 # 60 minutes
    private static final int EXPIRES_IN = 3600;
    //mobile_expires_in: 600 # 10 minutes
    private static final int MOBILE_EXPIRES_IN = 600;

    private static final String APP_NAME = "PIONEER_CORE";
    private static final String SECRET = "harambethegorilla";
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    private final TimeProvider timeProvider
            ;
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Autowired
    public TokenHelper(TimeProvider timeProvider){
        this.timeProvider = timeProvider;
    }

    public String getUserIdFromToken(String token) {
        String userId;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            userId = null;
        }
        return userId;
    }

    private Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String refreshToken(String token, Device device) {
        String refreshedToken;
        Date a = timeProvider.now();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate(device))
                    .signWith(SIGNATURE_ALGORITHM, SECRET)
                    .compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String generateToken(String userId, Device device) {
        String audience = generateAudience(device);
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(userId)//NOTICE HERE: USERID REPLACE USERNAME
                .setAudience(audience)
                .setIssuedAt(timeProvider.now())
                .setExpiration(generateExpirationDate(device))
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate(Device device) {
        long expiresIn = device.isTablet() || device.isMobile() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
        return new Date(timeProvider.now().getTime() + expiresIn * 1000);
    }

    public int getExpiredIn(Device device) {
        return device.isMobile() || device.isTablet() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
//        Person user = (Person) userDetails;
        final String userId = getUserIdFromToken(token);
//        final Date created = getIssuedAtDateFromToken(token);
        return (
                userId != null &&
                        userId.equals(userDetails.getUsername())
//                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
        );
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String getToken(HttpServletRequest request) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

}