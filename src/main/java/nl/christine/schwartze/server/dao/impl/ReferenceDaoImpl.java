/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import nl.christine.schwartze.server.dao.ReferenceDao;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.References;
import org.springframework.stereotype.Component;

@Component("referenceDao")
public class ReferenceDaoImpl implements ReferenceDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    @Override
    public References getReferences(String refType) {

        References existingReferences;

        TypedQuery<References> query = entityManager.createQuery(
                "select r from " + References.class.getSimpleName() + " r where r.type = :type ", References.class);
        try {
            existingReferences = query.setParameter("type", refType).getSingleResult();
        } catch (NoResultException nre) {
            existingReferences = null;
        }

        return existingReferences;

    }

    @Override
    public References updateReferences(References references) {

        if(references.getType() == null){
            return null;
        }
        References existingReferences = getReferences(references.getType());
        if (existingReferences == null) {
            if(references.getId() == 0){
                entityManager.persist(references);
            } else {
                entityManager.merge(references);
            }
            return references;
        }

        for (Link link : references.getLinks()) {
            if (link.getId() != 0) {
                existingReferences.getLinks().stream().filter(x -> x.getId() == link.getId()).findFirst().map(x -> {
                    x.setLinkName(link.getLinkName());
                    x.setLinkUrl(link.getLinkUrl());
                    return x;
                });
            } else {
                existingReferences.getLinks().add(link);
                entityManager.persist(link);
            }
        }

        return existingReferences;
    }
}
