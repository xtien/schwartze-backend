/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security.dao.impl;

import nl.christine.schwartze.server.security.dao.PrivilegeDao;
import nl.christine.schwartze.server.security.model.Privilege;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Component("privilegeDao")
@Profile("!test")
public class PrivilegeDaoImpl implements PrivilegeDao {

    @PersistenceContext(unitName = "userPU")
    private EntityManager entityManager;

    @Override
    public Privilege findByName(String name) {
        try {
            return entityManager
                    .createQuery("select p from Privilege p where p.name = :name", Privilege.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (
                NoResultException e) {
            return null;
        }
    }

    @Override
    public void persist(Privilege privilege) {
        entityManager.persist(privilege);
    }
}
