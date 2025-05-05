/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import nl.christine.schwartze.server.dao.CollectieDao;
import nl.christine.schwartze.server.model.Collectie;
import org.springframework.stereotype.Component;

@Component("collectionDao")
public class CollectieDaoImpl implements CollectieDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    @Override
    public Collectie getCollectie(int id) {
        return em.find(Collectie.class, id);
    }
}
