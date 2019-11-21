/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component("personDao")
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;


    org.apache.log4j.Logger logger = Logger.getLogger(PersonDaoImpl.class);

    @Override
    public Person updatePerson(Person person) {

        Person existingPerson = getPersonByName(person);
        if (existingPerson == null) {
            entityManager.persist(person);
        } else {
            person.setId(existingPerson.getId());
        }

        return person;
    }

    @Override
    public Person getPerson(int id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> getPersons() {

        TypedQuery<Person> query = entityManager.createQuery(
                "select a from " + Person.class.getSimpleName()
                        + " a ",
                Person.class);

        return query.getResultList();
    }

    @Override
    public Person getPersonByName(Person person) {

        Person existingPerson;

        TypedQuery<Person> query = entityManager.createQuery(
                "select a from " + Person.class.getSimpleName() + " a where a.firstName = :firstname and a.lastName = :lastname", Person.class);
        try {
            existingPerson = query.setParameter("firstname", person.getFirstName()).setParameter("lastname", person.getLastName()).getSingleResult();
        } catch (NoResultException nre) {
            existingPerson = null;
        }

        return existingPerson;
    }

    @Override
    public List<Person> getPeople(List<Integer> ids) {

        List<Person> people = new ArrayList<>();
        try {
            for (int id : ids) {
                people.add(getPerson(id));
            }
        } catch (Exception e) {
            logger.error("Error getting people", e);
        }
        return people;
    }

    @Override
    public List<Letter> getLettersForPerson(Optional<Integer> fromId, Optional<Integer> toId) {
        List<Letter> letters = new LinkedList<>();
        if(fromId.isPresent()){
            Person fromPerson = getPerson(fromId.get());
            letters.addAll(fromPerson.getLettersWritten());
        }
        if(toId.isPresent()){
            Person toPerson = getPerson(toId.get());
            letters.addAll(toPerson.getLettersReceived());
        }
        return letters;
    }

    @Override
    public Person addNewPerson(Person person) {
        entityManager.persist(person);
        return person;
    }
}
