/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.model.MyLocation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class LocationDaoImpl implements LocationDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    @Override
    public List<MyLocation> getLocations() {

        TypedQuery<MyLocation> query = entityManager.createQuery(
                "select a from " + MyLocation.class.getSimpleName()
                        + " a ",
                MyLocation.class);

        return query.getResultList();
    }

    @Override
    public  MyLocation getLocationByName(MyLocation location) {

        MyLocation existingLocation;

        TypedQuery<MyLocation> query = entityManager.createQuery(
                "select a from " + MyLocation.class.getSimpleName() + " a where a.locationName = :locationname ", MyLocation.class);
        try {
            existingLocation = query.setParameter("locationname", location.getName()).getSingleResult();
        } catch (NoResultException nre) {
            existingLocation = null;
        }

        return existingLocation;
    }

    @Override
    public MyLocation getLocation(int id) {
        return entityManager.find(MyLocation.class, id);
    }

}
