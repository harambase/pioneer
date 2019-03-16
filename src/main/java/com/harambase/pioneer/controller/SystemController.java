package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.security.auth.JwtAuthenticationRequest;
import com.harambase.pioneer.security.model.User;
import com.harambase.pioneer.security.model.UserTokenState;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.service.MonitorService;
import com.harambase.pioneer.service.PersonService;
import com.harambase.pioneer.social.QQAuthService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping(value = "/system", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MonitorService monitorService;
    private final PersonService personService;
    private final QQAuthService qqAuthService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public SystemController(MonitorService monitorService,
                            PersonService personService,
                            QQAuthService qqAuthService,
                            AuthenticationManager authenticationManager) {

        this.monitorService = monitorService;
        this.qqAuthService = qqAuthService;
        this.authenticationManager = authenticationManager;
        this.personService = personService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //访问登陆页面，然后会跳转到 QQ 的登陆页面
    @RequestMapping(value = "/login/qq", method = RequestMethod.GET)
    public ResponseEntity qqLogin() throws Exception {
        String uri = qqAuthService.getAuthorizationUrl();
        ResultMap resultMap = new ResultMap();
        resultMap.setData(uri);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    //qq授权后会回调此方法，并将code传过来
    @RequestMapping(value = "/login/qq/response", method = RequestMethod.GET)
    public void getQQCode(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //根据code获取token
            String accessToken = qqAuthService.getAccessToken(code);
            //根据openId判断用户是否已经绑定过
            String openId = qqAuthService.getOpenId(accessToken);

            ResultMap rsMap = personService.getUserByOpenId(openId);
            if (rsMap.getCode() == SystemConst.SUCCESS.getCode()) {
                User user = new User((Person) rsMap.getData());

                final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(), user.getAuthorities());
                // Inject into auth context
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jws = TokenHelper.generateToken(user.getUserId(), user.getRoles());
                int expiresIn = TokenHelper.getExpiredIn();

                // Return the token
                personService.updateLastLoginTime(user.getUsername());
                UserTokenState token = new UserTokenState(jws, expiresIn);
                response.sendRedirect("http://www.xianfengedu.cn/eas#/login?token=" + token.getAccess_token());

            } else {
//                resultMap.setCode(SystemConst.NEEDREG.getCode());
//                resultMap.setData(openId);
            }
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
//            resultMap.setCode(SystemConst.FAIL.getCode());
        }

//        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        ResultMap resultMap = new ResultMap();
        try {
            //System.out.println(passwordEncoder.encode(authenticationRequest.getPassword()));
            // Perform the auth
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            // Inject into auth context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // token creation
            User user = (User) authentication.getPrincipal();
            String jws = TokenHelper.generateToken(user.getUserId(), user.getRoles());
            int expiresIn = TokenHelper.getExpiredIn();

            // Return the token
            personService.updateLastLoginTime(authenticationRequest.getUsername());
            resultMap.setData(new UserTokenState(jws, expiresIn));
            resultMap.setCode(SystemConst.SUCCESS.getCode());

        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            resultMap.setCode(SystemConst.FAIL.getCode());
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/verify", method = RequestMethod.POST)
    public ResponseEntity verifyUser(@RequestBody Person user) {
        ResultMap resultMap = new ResultMap();
        Person p = personService.verifyUser(user);
        if (p != null) {
            resultMap.setData(p);
            resultMap.setCode(SystemConst.SUCCESS.getCode());
        } else
            resultMap.setCode(SystemConst.FAIL.getCode());

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/verify/{userId}", method = RequestMethod.GET)
    public ResponseEntity getByUserId(@PathVariable String userId, @RequestParam String token) {
        ResultMap resultMap = new ResultMap();
        if (StringUtils.isNotEmpty(token) && userId.equals(TokenHelper.getUserIdFromToken(token))) {
            Person p = (Person) personService.get(userId).getData();
            if (p != null) {
                resultMap.setData(p);
                resultMap.setCode(SystemConst.SUCCESS.getCode());
            } else
                resultMap.setCode(SystemConst.FAIL.getCode());
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/reset/password/{userId}", method = RequestMethod.PUT)
    public ResponseEntity passwordReset(@PathVariable String userId, @RequestBody Person user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        ResultMap resultMap = personService.updatePerson(userId, user);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.POST)
    public ResponseEntity refreshAuthenticationToken(HttpServletRequest request, Principal principal) {
        ResultMap resultMap = new ResultMap();

        String authToken = TokenHelper.getToken(request);

        if (authToken != null && principal != null) {
            String refreshedToken = TokenHelper.refreshToken(authToken);
            int expiresIn = TokenHelper.getExpiredIn();
            resultMap.setData(new UserTokenState(refreshedToken, expiresIn));
        } else {
            resultMap.setData(new UserTokenState());
        }

        resultMap.setCode(SystemConst.SUCCESS.getCode());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authToken = TokenHelper.getToken(request);
        response.sendRedirect("/login");
    }

    @PreAuthorize("hasAnyRole('USER')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity systemInfo() {
        ResultMap message = monitorService.systemInfo();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/relation", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SYSTEM','ADMIN')")
    public ResponseEntity relationChart() {
        ResultMap resultMap = monitorService.relationChart();
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/chart", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SYSTEM','ADMIN')")
    public ResponseEntity userChart() {
        ResultMap resultMap = monitorService.userChart();
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
