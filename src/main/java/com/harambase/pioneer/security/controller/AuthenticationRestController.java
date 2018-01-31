package com.harambase.pioneer.security.controller;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.security.JwtAuthenticationRequest;
import com.harambase.pioneer.security.JwtTokenUtil;
import com.harambase.pioneer.security.http.HttpKit;
import com.harambase.pioneer.security.service.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        JwtTokenUtil jwtTokenUtil,
                                        UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        HaramMessage haramMessage = new HaramMessage();
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, null);

        // Return the token
        haramMessage.setData(new JwtAuthenticationResponse(token));
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequestMapping(value = "/jwt/refresh", method = RequestMethod.GET)
    public ResponseEntity refreshAndGetAuthenticationToken() {
        HaramMessage haramMessage = new HaramMessage();

        HttpServletRequest request = HttpKit.getRequest();
        String authToken = request.getHeader("Authorization");
        final String token = authToken.substring(7);
//        String username = jwtTokenUtil.getUsernameFromToken(token);

        String refreshedToken = jwtTokenUtil.refreshToken(token);
        haramMessage.setData(new JwtAuthenticationResponse(refreshedToken));
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

}
