/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
public class TestGetAllPeople {

    @Autowired
    private PersonService personService;

    @Autowired
    private LetterService letterservice;

    @Test
    public void testGetPeople() {

        List<Person> list = personService.getAllPeople();

        Person person1 = new Person();
        person1.setName("Lizzy");
        Person person2 = new Person();
        person2.setName("Therese");
        person2.setLastName("Schwartze");
        Person person3 = new Person();
        person3.setLastName("Moes");

        Letter letter1 = new Letter();
        letter1.setComment("comment 1");
        letter1.getSenders().add(person1);
        Letter letter2 = new Letter();
        letter2.setComment("comment 2");
        letter2.getSenders().add(person1);
        Letter letter3 = new Letter();
        letter3.setComment("comment 3");
        letter3.getSenders().add(person1);
        Letter letter4 = new Letter();
        letter4.setComment("comment 4");
        letter4.getSenders().add(person1);

        person1.addLetterWritten(letter1);
        person1.addLetterWritten(letter2);
        person1.addLetterWritten(letter3);
        person1.addLetterWritten(letter4);

        int id1 =  personService.addPerson(person1).getId();
        int id2 =  personService.addPerson(person2).getId();
        int id3 =  personService.addPerson(person3).getId();

        List<Person> people = personService.getAllPeople();

        assertEquals(3+list.size(), people.size());
        assertEquals("Moes", people.get(0).getLastName());
    }
}
