package com.harambase.pioneer.controller;

import com.harambase.pioneer.helper.DeviceHelper;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.constant.FlagDict;
import com.harambase.pioneer.security.model.User;
import com.harambase.pioneer.security.model.UserTokenState;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.security.auth.JwtAuthenticationRequest;
import com.harambase.pioneer.service.MonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;
    private final DeviceHelper deviceHelper;

    @Autowired
    public SystemController(MonitorService monitorService,
                            AuthenticationManager authenticationManager,
                            DeviceHelper deviceHelper) {

        this.monitorService = monitorService;
        this.authenticationManager = authenticationManager;
        this.deviceHelper = deviceHelper;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) {

        HaramMessage haramMessage = new HaramMessage();
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
            String jws = TokenHelper.generateToken(user.getUserId(), device);
            int expiresIn = TokenHelper.getExpiredIn(device);

            // Return the token

            haramMessage.setData(new UserTokenState(jws, expiresIn));
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            return new ResponseEntity<>(haramMessage , HttpStatus.OK);

        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
            haramMessage.setCode(FlagDict.FAIL.getV());
            return new ResponseEntity<>(haramMessage , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/token/refresh", method = RequestMethod.POST)
        public ResponseEntity refreshAuthenticationToken(HttpServletRequest request, Principal principal) {
        HaramMessage haramMessage = new HaramMessage();
        
        String authToken = TokenHelper.getToken(request);
        Device device = deviceHelper.getCurrentDevice(request);

        if (authToken != null && principal != null) {
            String refreshedToken = TokenHelper.refreshToken(authToken, device);
            int expiresIn = TokenHelper.getExpiredIn(device);
            haramMessage.setData(new UserTokenState(refreshedToken, expiresIn));
        } else {
            haramMessage.setData(new UserTokenState());
        }
        
        haramMessage.setCode(FlagDict.SUCCESS.getV());
        return new ResponseEntity<>(haramMessage , HttpStatus.OK);
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        SecurityUtils.getSubject().logout();
        String authToken = TokenHelper.getToken(request);

        response.sendRedirect("/login");
    }

//    @RequestMapping(value = "/swagger")
//    @PreAuthorize("hasRole('ADMIN')")
//    public void swagger(HttpServletResponse response) throws Exception {
//        response.setHeader("userId", SessionUtil.getUserId());
//        response.sendRedirect("http://localhost:8080/?userId=" + SessionUtil.getUserId());
//    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SYSTEM','ADMIN')")
    public ResponseEntity systemInfo() {
        HaramMessage message = monitorService.systemInfo();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/relation", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SYSTEM','ADMIN')")
    public ResponseEntity relationChart() {
        HaramMessage haramMessage = monitorService.getRelationChart();
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/count", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SYSTEM','ADMIN')")
    public ResponseEntity userCount() {
        HaramMessage haramMessage = monitorService.getUserChart();
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

}
