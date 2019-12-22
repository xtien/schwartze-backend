/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.security;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LoginRequest;
import nl.christine.schwartze.server.controller.result.LoginResult;
import nl.christine.schwartze.server.security.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
@Profile("!test")
public class UserController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResult> login() {

        HttpStatus status = HttpStatus.OK;
        LoginResult loginResult = new LoginResult();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserDetails user = userDetailsService.loadUserByUsername(currentPrincipalName);
        loginResult.setAuthorities(user.getAuthorities());

         return new ResponseEntity<>(loginResult, status);
    }
}
