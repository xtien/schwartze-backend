/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.result.CombinePersonResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.callback.TextInputCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * User: christine
 * Date: 12/29/18 12:15 PM
 */
@Component("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Autowired
    private LetterDao letterDao;

    @Override
    @Transactional("transactionManager")
    public Person getPerson(int id) {
        Person resultPerson = personDao.getPerson(id);
        List<Letter> lettersWritten = resultPerson.getLettersWritten();
        resultPerson.getLettersReceived();
        return resultPerson;
    }

    @Override
    @Transactional("transactionManager")
    public Person updatePerson(Person person) {
        Person existingPerson = null;
        try {
            existingPerson = personDao.updatePerson(person);
        } catch (Exception e) {
            logger.error("Error updating person", e);
        }
        return existingPerson;
    }

    @Override
    @Transactional("transactionManager")
    public Person addPerson(Person person) {

        Person updatedPerson = personDao.addNewPerson(person);
        return updatedPerson;
    }

    @Override
    @Transactional("transactionManager")
    public CombinePersonResult getCombinePersons(int id1, int id2) {

        CombinePersonResult result = new CombinePersonResult();
        result.setPerson1(getPerson(id1));
        result.setPerson2(getPerson(id2));
        return result;
    }

    @Override
    @Transactional("transactionManager")
    public CombinePersonResult putCombinePersons(int id1, int id2) {

        CombinePersonResult result = new CombinePersonResult();
        result.setPerson1(merge(getPerson(id1), getPerson(id2)));
        return result;
    }

    @Override
    public List<Person> getPeople(List<Integer> ids) {
        List<Person> people = new ArrayList<>();
        try {
            for (int id : ids) {
                people.add(personDao.getPerson(id));
            }
        } catch (Exception e) {
            logger.error("Error getting people", e);
        }
        return people;
    }

    @Override
    public List<Person> getAllPeople() {
        return personDao.getAllPeople();
    }

    @Override
    public int deletePerson(int id) {
        Person existingPerson = getPerson(id);
        if(isEmpty(existingPerson.getLettersWritten()) && isEmpty(existingPerson.getLettersReceived())){
            personDao.deletePerson(id);
        }
        return 0;
    }

    private Person merge(Person person1, Person person2) {

        person2.getLettersReceived().stream().forEach(x -> {
            person1.getLettersReceived().add(x);
             x.getRecipients().add(person1);
             x.getRecipients().remove(person2);
        });

        person2.getLettersWritten().stream().forEach(x -> {
            person1.getLettersWritten().add(x);
            x.getSenders().add(person1);
            x.getSenders().remove(person2);
        });

        if (StringUtils.isEmpty(person1.getFirstName())) {
            person1.setFirstName(person2.getFirstName());
        }
        if (StringUtils.isEmpty(person1.getMiddleName())) {
            person1.setMiddleName(person2.getMiddleName());
        }
        if (StringUtils.isEmpty(person1.getLastName())) {
            person1.setLastName(person2.getLastName());
        }
        if (StringUtils.isNotEmpty(person2.getComment())) {
            person1.setComment(person1.getComment() + "\n" + person2.getComment());
        }
        if (StringUtils.isNotEmpty(person2.getLinks())) {
            person1.setLinks(person1.getLinks() + "\n" + person2.getLinks());
        }

        person2.getLettersReceived().clear();
        person2.getLettersWritten().clear();
        personDao.deletePerson(person2);

        return person1;
    }
}
