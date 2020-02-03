/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.controller.result.CombinePersonResult;
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
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
public class TestCombinePerson {

    @Autowired
    private PersonService personService;

    @Autowired
    private LetterService letterservice;

    @Test
    public void testCombine() {

        List<Person> list = personService.getAllPeople();

        Person person1 = new Person();
        person1.setFirstName("Anna1");
        person1.setLastName(("van Gogh"));

        Person person2 = new Person();
        person2.setFirstName("Anna2");
        person2.setLastName(("van Gogh"));

        Letter letter1 = new Letter();
        letter1.setComment("comment 1");
        letter1.getSenders().add(person1);

        Letter letter2 = new Letter();
        letter2.setComment("comment 2");
        letter2.getSenders().add(person1);

        Letter letter3 = new Letter();
        letter3.setComment("comment 3");
        letter3.getSenders().add(person2);

        Letter letter4 = new Letter();
        letter4.setComment("comment 4");
        letter4.getRecipients().add(person2);

        person1.addLetterReceived(letter1);
        person1.addLetterReceived(letter2);
        int id1 = personService.addPerson(person1).getId();

        person2.addLetterReceived(letter3);
        person2.addLetterWritten(letter4);
        int id2 = personService.addPerson(person2).getId();

        Person person3 = new Person();
        person3.setFirstName("Lizzy");
        person3.setLastName(("Ansingh"));
        personService.addPerson(person3);

        CombinePersonResult result = personService.getCombinePersons(id1, id2);

        assertEquals("Anna1", result.getPerson1().getFirstName());
        assertEquals("Anna2", result.getPerson2().getFirstName());

        CombinePersonResult result2 = personService.putCombinePersons(id1, id2);

        assertEquals("Anna1", result2.getPerson1().getFirstName());
        assertNull(result2.getPerson2());

        List<Person> allPeople = personService.getAllPeople();
        assertEquals(2+list.size(), allPeople.size());

        List<Letter> lettersFrom = letterservice.getLettersFromPerson(id1);
        assertEquals(3, lettersFrom.size());
    }
}
