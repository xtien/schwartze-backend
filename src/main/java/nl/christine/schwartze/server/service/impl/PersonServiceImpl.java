/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.callback.TextInputCallback;

/**
 * User: christine
 * Date: 12/29/18 12:15 PM
 */
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Override
    public Person getPerson(int id) {
        return personDao.getPerson(id);
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
    public Person addPerson(Person person) {

        Person updatedPerson = personDao.addNewPerson(person);
        return updatedPerson;
    }
}
