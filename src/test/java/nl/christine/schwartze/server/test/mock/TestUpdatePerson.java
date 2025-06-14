/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.impl.PersonServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * User: christine
 * Date: 12/29/18 12:17 PM
 */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdatePerson {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonDao personDao;

    @Mock
    private Person person;

    private int personId = 5;

    @Test
    public void testUpdatePerson() throws IOException {

        when(person.getId()).thenReturn(personId);
        when(person.getComment()).thenReturn("comment");
        when(personDao.getPerson(personId)).thenReturn(person);
        when(personDao.updatePerson(any(Person.class))).thenReturn(person);

        personService.updatePerson(person);

        Person resultingPerson = personService.getPerson(personId);

        Assert.assertNotNull(resultingPerson);
        Assert.assertEquals("comment", resultingPerson.getComment());
    }

}
