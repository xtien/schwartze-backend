/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security;

import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.security.dao.PrivilegeDao;
import nl.christine.schwartze.server.security.dao.RoleDao;
import nl.christine.schwartze.server.security.dao.UserDao;
import nl.christine.schwartze.server.security.model.Privilege;
import nl.christine.schwartze.server.security.model.Role;
import nl.christine.schwartze.server.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@DependsOn({"schwartzeProperties"})
@Profile("!test")
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private String defaultUser;
    private String defaultPassword;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;

    @Autowired
    private PrivilegeDao privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SchwartzeProperties properties;

    @PostConstruct
    private void init(){
        defaultUser = properties.getProperty("defaultUser");
        defaultPassword = properties.getProperty("defaultPassword");
    }

    @Override
    @Transactional("userTransactionManager")
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup || userRepository.exists(defaultUser)){
            return;
        }

        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_USER");
        User user = new User();
        user.setUserName(defaultUser);
        user.setPassword(passwordEncoder.encode(defaultPassword));
        user.setRoles(Arrays.asList(adminRole, userRole));
        user.setEnabled(true);
        userRepository.persist(user);

        alreadySetup = true;
    }

    @Transactional("userTransactionManager")
    public Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.persist(privilege);
        }
        return privilege;
    }

    @Transactional("userTransactionManager")
    public Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.persist(role);
        }
        return role;
    }
}
