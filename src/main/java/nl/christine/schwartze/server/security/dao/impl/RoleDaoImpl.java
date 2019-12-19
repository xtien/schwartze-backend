/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security.dao.impl;

import nl.christine.schwartze.server.security.dao.RoleDao;
import nl.christine.schwartze.server.security.dao.UserDao;
import nl.christine.schwartze.server.security.model.Privilege;
import nl.christine.schwartze.server.security.model.Role;
import nl.christine.schwartze.server.security.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Component("roleDao")
@Profile("!test")
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext(unitName = "userPU")
    private EntityManager entityManager;

    @Override
    public Role findByName(String name) {
        try {
            return entityManager
                    .createQuery("select r from Role r where r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (
                NoResultException e) {
            return null;
        }
    }

    @Override
    public void persist(Role role) {
        entityManager.persist(role);
    }
}
