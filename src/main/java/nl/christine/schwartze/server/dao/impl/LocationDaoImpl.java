/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Text;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component("locationDao")
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
    public MyLocation getLocationByName(MyLocation location) {

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

    @Override
    public MyLocation addNewLocation(MyLocation location) {
        entityManager.persist(location);
        return location;
    }

    @Override
    public void deleteLocation(int id) throws LocationNotFoundException {
        MyLocation existingLocation = entityManager.find(MyLocation.class, id);
        if (existingLocation != null) {
            existingLocation.setLettersFrom(null);
            existingLocation.setLettersTo(null);
            existingLocation.setLinks(null);
            entityManager.remove(existingLocation);
        } else {
            throw new LocationNotFoundException();
        }
    }

    @Override
    public Text getLocationText(int id) {
        MyLocation location = getLocation(id);
        if (location.getText() == null) {
            Text text = new Text();
            entityManager.persist(text);
            location.setText(text);
        }
        return location.getText();
    }

    @Override
    public void merge(MyLocation location) {
        entityManager.merge(location);
    }

    @Override
    public void deleteLocation(MyLocation location) {
        entityManager.remove(location);
    }

    @Override
    public List<Letter> getLettersForLocation(Optional<Integer> locationId) {
        List<Letter> letters = new LinkedList<>();
        if (locationId.isPresent()) {
            MyLocation location = getLocation(locationId.get());
            letters.addAll(location.getLettersFrom());
            letters.addAll(location.getLettersTo());
        }
        return letters;
    }

    @Override
    public MyLocation saveLocation(MyLocation location) {
        if (location.getId() != 0) {
            entityManager.merge(location);
            location = entityManager.find(MyLocation.class, location.getId());
        } else {
            entityManager.persist(location);
        }
        return location;
    }

    @Override
    public void persist(MyLocation location) {
        entityManager.persist(location);
    }

}
