/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.controller.user.UserController;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
@ActiveProfiles("test")
public class TestUpdateLetter {

    @Autowired
    private LetterService letterService;

    @Autowired
    private PersonService personService;

    @Test
    public void testUpdateLetter() {

        Letter letter = createLetter(1);
        letterService.addLetter(letter);
        Person person3 = createPerson(3, 3);
        person3 = personService.addPerson(person3);
        Person person4 = createPerson(4, 4);
        person4 = personService.addPerson(person4);

        letter.getSenders().remove(0);
        letter.getRecipients().remove(0);
        letter.getSenders().add(person3);
        letter.getRecipients().add(person4);

        letterService.updateLetter(letter);

        Letter updatedLetter = letterService.getLetterByNumber(letter.getNumber());

        assertNotNull(updatedLetter);
    }

    private Letter createLetter(int i) {
        Letter letter = new Letter();
        letter.setNumber(i);
        letter.setComment("comment" + i);
        letter.getSenders().add(createPerson(i, 1));
        letter.getSenders().add(createPerson(i, 2));
        letter.getRecipients().add(createPerson(i, 3));
        letter.getRecipients().add(createPerson(i, 4));
        return letter;
    }

    private Person createPerson(int i, int j) {
        Person person = new Person();
        //   person.setId(i * 10 + j);
        person.setFirstName("first" + i + j);
        person.setLastName("last" + i + j);
        return person;
    }
}
