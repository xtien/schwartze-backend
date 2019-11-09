/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    public int updatePerson(Person person) {
        int result = -1;
        try {
            Person existingPerson = personDao.getPerson(person.getId());
            if (existingPerson != null) {
                existingPerson.setComment(person.getComment());
                existingPerson.setLinks(person.getLinks());
                result = 0;
            }
        } catch (Exception e) {
            logger.error("Error updating person",e);
        }
        return result;
    }
}
