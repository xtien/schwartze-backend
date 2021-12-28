/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security;

import nl.christine.schwartze.server.security.dao.RoleDao;
import nl.christine.schwartze.server.security.dao.UserDao;
import nl.christine.schwartze.server.security.model.Privilege;
import nl.christine.schwartze.server.security.model.Role;
import nl.christine.schwartze.server.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * https://www.baeldung.com/spring-security-authentication-with-a-database
 * https://www.baeldung.com/manually-set-user-authentication-spring-security
 */
@Service("userDetailsService")
@Profile("!test")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  UserDao userDao;
    @Autowired
    private  RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String userName) {

        User user = userDao.findByName(userName);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(Arrays.asList(
                            roleDao.findByName("ROLE_USER"))));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
