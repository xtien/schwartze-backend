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
import nl.christine.schwartze.server.service.TextService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
@ActiveProfiles("test")
public class TestUpdateLetter {

    private int letterNumber = 17;
    @Autowired
    private LetterService letterService;

    @Autowired
    private PersonService personService;

    @Autowired
    private TextService textService;


    @Test
    public void testUpdateLetter() throws InterruptedException {

        Letter letter1 = createLetter(1);
        letter1.setNumber(letterNumber);
        for(Person p : letter1.getRecipients()){
            personService.addPerson(p);
        }
        for(Person p : letter1.getSenders()){
            personService.addPerson(p);
        }
        letterService.addLetter(letter1);

        Letter letter2 = createLetter(1);
        letter2.setNumber(letterNumber);
        for(Person p : letter2.getRecipients()){
            personService.addPerson(p);
        }
        for(Person p : letter2.getSenders()){
            personService.addPerson(p);
        }

        Person person3 = createPerson(3, 3);
        person3 = personService.addPerson(person3);
        Person person4 = createPerson(4, 4);
        person4 = personService.addPerson(person4);

        letter2.getSenders().remove(0);
        letter2.getRecipients().remove(0);
        letter2.getSenders().add(person3);
        letter2.getRecipients().add(person4);

        letterService.updateLetter(letter2);

        Letter updatedLetter = letterService.getLetterByNumber(letterNumber);

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
