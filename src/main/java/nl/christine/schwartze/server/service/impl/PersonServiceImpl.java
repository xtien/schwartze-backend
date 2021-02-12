/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.CombinePersonResult;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.dao.LinkDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * User: christine
 * Date: 12/29/18 12:15 PM
 */
@Component("personService")
public class PersonServiceImpl implements PersonService {

    private final Comparator<Person> compareByName;
    private final Comparator<Person> compareByLastName;

    @Autowired
    private PersonDao personDao;

    Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Autowired
    private LinkDao linkDao;

    public PersonServiceImpl() {
        compareByName = Comparator
                .comparing(Person::getName, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(Person::getLastName, Comparator.nullsFirst(Comparator.naturalOrder()));
        compareByLastName = Comparator
                .comparing(Person::getLastName, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(Person::getName, Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    @Override
    @Transactional("transactionManager")
    public Person getPerson(int id) {
        Person resultPerson = personDao.getPerson(id);
        resultPerson.getLettersWritten();
        resultPerson.getLettersReceived();
        resultPerson.setHasLettersFrom(resultPerson.getLettersWritten().size() > 0);
        resultPerson.setHasLettersTo(resultPerson.getLettersReceived().size() > 0);
        return resultPerson;
    }

    @Override
    @Transactional("transactionManager")
    public Person updatePerson(Person person) {
        validate(person);
        Person existingPerson = null;
        try {
            existingPerson = personDao.updatePerson(person);
        } catch (Exception e) {
            logger.error("Error updating person", e);
        }
        return existingPerson;
    }

    private void validate(Person person) {
        if (StringUtils.isNotEmpty(person.getComment()) && person.getComment().length() > 255) {
            person.setComment(person.getComment().substring(0, 255));
        }
    }

    @Override
    @Transactional("transactionManager")
    public Person addPerson(Person person) {

        validate(person);
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
    @Transactional("transactionManager")
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
    @Transactional("transactionManager")
    public List<Person> getAllPeople() {
        return personDao.getAllPeople().stream().sorted(compareByName).collect(Collectors.toList());
    }

    @Override
    @Transactional("transactionManager")
    public List<Person> getAllPeopleByLastName() {
        return personDao.getAllPeople().stream().sorted(compareByLastName).collect(Collectors.toList());
    }

    @Override
    @Transactional("transactionManager")
    public int deletePersonIfNoChildren(int id) {
        Person existingPerson = getPerson(id);
        if (isEmpty(existingPerson.getLettersWritten()) && isEmpty(existingPerson.getLettersReceived())) {
            personDao.deletePerson(id);
        }
        return 0;
    }

    @Override
    @Transactional("transactionManager")
    public Text getText(int id) {
        return personDao.getPersonText(id);
    }

    private Person merge(Person person1, Person person2) {

        personDao.merge(person1);
        personDao.merge(person2);

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

        if (StringUtils.isEmpty(person1.getName())) {
            person1.setName(person2.getName());
        }
        if (StringUtils.isEmpty(person1.getFullName())) {
            person1.setFullName(person2.getFullName());
        }
        if (StringUtils.isEmpty(person1.getTussenvoegsel())) {
            person1.setTussenvoegsel(person2.getTussenvoegsel());
        }
        if (StringUtils.isEmpty(person1.getLastName())) {
            person1.setLastName(person2.getLastName());
        }
        if (StringUtils.isEmpty(person1.getDateOfBirth())) {
            person1.setDateOfBirth(person2.getDateOfBirth());
        }
        if (StringUtils.isEmpty(person1.getDateOfDeath())) {
            person1.setDateOfDeath(person2.getDateOfDeath());
        }
        if (StringUtils.isNotEmpty(person2.getComment())) {
            person1.setComment(person1.getComment() + "\n" + person2.getComment());
        }
        if (!CollectionUtils.isEmpty(person2.getLinks())) {
            if (person1.getLinks() == null) {
                person1.setLinks(new ArrayList<>());
            }
            for (Link link : person2.getLinks()) {
                link.setPerson(person1);
            }
            person1.addLinks(person2.getLinks());
        }

        if (person2.getText() != null && StringUtils.isNotEmpty(person2.getText().getTextString())) {
            if (person1.getText() == null || StringUtils.isEmpty(person1.getText().getTextString())) {
                person1.setText(person2.getText());
            } else {
                person1.getText().setTextString(person1.getText().getTextString() + " " + person2.getText().getTextString());
            }
        }

        person2.getLettersReceived().clear();
        person2.getLettersWritten().clear();
        person2.getLinks().clear();
        personDao.deletePerson(person2);

        return person1;
    }


    @Override
    @Transactional("transactionManager")
    public EditLinkResult editLink(EditLinkRequest request) {

        EditLinkResult result = new EditLinkResult();
        Person person = getPerson(request.getPersonId());

        if (person == null) {
            return result;
        }

        if (request.getLinkId() == null) {
            Link link = new Link(request.getLinkName(), request.getLinkUrl());
            link.setPerson(person);
            person.getLinks().add(link);
            linkDao.persist(link);
        } else {
            Optional<Link> link = person.getLinks().stream().filter(x -> x.getId() == request.getLinkId()).findFirst();
            if (link.isPresent()) {
                link.get().setLinkName(request.getLinkName());
                link.get().setLinkUrl(request.getLinkUrl());
            }
        }

        result.setPerson(person);
        return result;
    }

}
