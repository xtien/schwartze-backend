/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.model.Text;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@SuppressWarnings("JpaQlInspection")
@Component("personDao")
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    Logger logger = Logger.getLogger(PersonDaoImpl.class);

    @Override
    public Person updatePerson(Person person) {

        Person existingPerson = getPerson(person.getId());
        if (existingPerson == null) {
            entityManager.persist(person);
        } else {
            existingPerson.setLastName(person.getLastName());
            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setFullName(person.getFullName());
            existingPerson.setTussenvoegsel(person.getTussenvoegsel());
            existingPerson.setComment(person.getComment());
            existingPerson.setImageCaption(person.getImageCaption());
            existingPerson.setImageUrl(person.getImageUrl());
            existingPerson.setDateOfBirth(person.getDateOfBirth());
            existingPerson.setDateOfDeath(person.getDateOfDeath());
            existingPerson.setPlaceOfBirth(person.getPlaceOfBirth());
            existingPerson.setPlaceOfDeath(person.getPlaceOfDeath());
        }

        return person;
    }

    @Override
    public List<Person> getAllPeople() {
        TypedQuery<Person> query = entityManager.createQuery(
                "select a from " + Person.class.getSimpleName()
                        + " a order by a.lastName",
                Person.class);
        return query.getResultList();
    }

    @Override
    public void deletePerson(Person person) {
        entityManager.remove(person);
    }

    @Override
    public void deletePerson(int id) {
        Person person = getPerson(id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    @Override
    public void merge(Person person) {
        entityManager.merge(person);
    }

    @Override
    public Text getPersonText(int id) {
        Person person = getPerson(id);
        if (person.getText() == null) {
            Text text = new Text();
            entityManager.persist(text);
            person.setText(text);
        }
        return person.getText();
    }

    @Override
    public void persistIfNotExist(Person p) {
        if (p.getId() == 0) {
            entityManager.persist(p);
        }
    }

    @Override
    public void savePerson(Person person) {
        if (person.getId() != 0) {
            entityManager.merge(person);
        } else {
            entityManager.persist(person);
        }
    }

    @Override
    public Person persist(Person person) {
        entityManager.persist(person);
        return person;
    }

    @Override
    public List<Person> search(String searchTerm) {
        TypedQuery<Person> query = entityManager.createQuery(
                "select a from " + Person.class.getSimpleName() +
                        " a  where LOWER(a.firstName) LIKE :searchTerm" +
                        " or LOWER(a.fullName) LIKE :searchTerm" +
                        " or LOWER(a.lastName) LIKE :searchTerm",
                Person.class);
        return query.setParameter("searchTerm", "%" + searchTerm.toLowerCase(Locale.ROOT) + "%").getResultList();
    }

    @Override
    public List<Person> searchFirstAndLastName(String searchTerm) {

        String array[] = searchTerm.split(" ");
        final String firstName = array[0];
        final String lastName = searchTerm.substring(searchTerm.indexOf(" ") + 1);

        TypedQuery<Person> query = entityManager.createQuery(
                "select a from " + Person.class.getSimpleName() +
                        " a  where LOWER(a.firstName) = :firstName" +
                        " and LOWER(a.lastName) = :lastName",
                Person.class);
        List<Person> list = query.setParameter("firstName", firstName.toLowerCase(Locale.ROOT)).setParameter("lastName", lastName.toLowerCase(Locale.ROOT)).getResultList();
        return list;
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
                "select a from " + Person.class.getSimpleName() + " a where a.name = :firstname and a.lastName = :lastname", Person.class);
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
        if (fromId.isPresent()) {
            Person fromPerson = getPerson(fromId.get());
            letters.addAll(fromPerson.getLettersWritten());
        }
        if (toId.isPresent()) {
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
