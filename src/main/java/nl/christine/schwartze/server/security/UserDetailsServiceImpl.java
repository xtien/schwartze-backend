/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security;

import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.security.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;

/**
 * https://www.baeldung.com/spring-security-authentication-with-a-database
 * https://www.baeldung.com/manually-set-user-authentication-spring-security
 */
@Service("userDetailsService")
@DependsOn({"schwartzeProperties"})
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;

    private String defaultUser;
    private String defaultPassword;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao, AuthenticationManager authenticationManager, SchwartzeProperties properties) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        defaultUser = properties.getProperty("defaultUser");
        defaultPassword = properties.getProperty("defaultPassword");
    }

    @PostConstruct
    public void init() {

//        try {
//            UserDetails user = loadUserByUsername("christine");
//        } catch (UsernameNotFoundException e) {
//
//            UsernamePasswordAuthenticationToken authReq
//                    = new UsernamePasswordAuthenticationToken(defaultUser, defaultPassword);
//            Authentication auth = authenticationManager.authenticate(authReq);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
    }

    @Override
    @Transactional("userTransactionManager")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try{
            User curruser = userDao.findByUsername(username);
            UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), true,
                    true, true, true, AuthorityUtils.createAuthorityList(curruser.getRole()));
            System.out.println("ROLE: " + curruser.getRole());
            return user;

        } catch (NoResultException nre){
            throw new UsernameNotFoundException("user not found");
        }
     }
}