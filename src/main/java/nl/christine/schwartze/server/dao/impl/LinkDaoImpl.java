/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.christine.schwartze.server.dao.LinkDao;
import nl.christine.schwartze.server.model.Link;
import org.springframework.stereotype.Component;

@Component("linkDao")
public class LinkDaoImpl implements LinkDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    @Override
    public void deleteLink(Integer linkId) {
        Link link = em.find(Link.class, linkId);
        if (link != null) {
            if (link.getLocation() != null) {
                link.getLocation().getLinks().remove(link);
            }
            if (link.getPerson() != null) {
                link.getPerson().getLinks().remove(link);
            }
            em.remove(link);
        }
    }

    @Override
    public void remove(Link link) {
        em.remove(link);
    }

    @Override
    public void persist(Link link) {
        em.persist(link);
    }
}
