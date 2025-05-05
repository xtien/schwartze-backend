/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import nl.christine.schwartze.server.security.dao.RoleDao;
import nl.christine.schwartze.server.security.model.Role;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
