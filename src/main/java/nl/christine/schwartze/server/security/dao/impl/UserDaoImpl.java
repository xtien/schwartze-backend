/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security.dao.impl;

import nl.christine.schwartze.server.security.User;
import nl.christine.schwartze.server.security.dao.UserDao;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component("userDao")
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "userPU")
    private EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        return entityManager
                .createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
