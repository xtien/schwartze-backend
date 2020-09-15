/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.user;

import nl.christine.schwartze.server.controller.request.LogoutRequest;
import nl.christine.schwartze.server.controller.result.LoginResult;
import nl.christine.schwartze.server.controller.result.LogoutResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 7200)
public class UserController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/login")
    public ResponseEntity<LoginResult> login() {

        HttpStatus status = HttpStatus.OK;
        LoginResult loginResult = new LoginResult();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserDetails user = userDetailsService.loadUserByUsername(currentPrincipalName);
        loginResult.setAuthorities(user.getAuthorities());

        return new ResponseEntity<>(loginResult, status);
    }

    @PostMapping(value = "/user/signout")
    public ResponseEntity<LogoutResult> logout(@RequestBody LogoutRequest request) {

        HttpStatus status = HttpStatus.OK;
        LogoutResult logoutResult = new LogoutResult();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }

        return new ResponseEntity<>(logoutResult, status);
    }
}
