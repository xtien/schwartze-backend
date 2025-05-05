/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security.dao.impl;

import nl.christine.schwartze.server.security.dao.UserDao;
import nl.christine.schwartze.server.security.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Component("userDao")
@Profile("!test")
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "userPU")
    private EntityManager entityManager;

    @Override
    public User findByName(String username) {
        try {
            return entityManager
                    .createQuery("select u from User u where u.userName = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void persist(User user) {
        entityManager.persist(user);
    }

    @Override
    public boolean exists(String defaultUser) {
        return findByName(defaultUser) != null;
    }
}
