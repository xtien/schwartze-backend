/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import nl.christine.schwartze.server.controller.UpdatePersonController;
import nl.christine.schwartze.server.controller.request.UpdatePersonRequest;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Random;

import static org.mockito.Mockito.when;

/**
 * User: christine
 * Date: 12/29/18 12:17 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdatePersonController {

    @InjectMocks
    private UpdatePersonController updatePersonController;

    @Mock
    private PersonService personService;

    private int personId = 12;
    private String testComment = "just commenting ";

    @Test
    public void testUpdatePerson() throws IOException {

        String updatedComment = "more commenting";

        Person p = new Person();
        p.setId(personId);
        p.setComment(testComment);
        Person updatedPerson = new Person();
        updatedPerson.setId(personId);
        updatedPerson.setComment(updatedComment);

        when(personService.updatePerson(p)).thenReturn(updatedPerson);
        testComment += new Random().nextInt(999);
        UpdatePersonRequest request = new UpdatePersonRequest();
        request.setPerson(p);
        PersonResult result = updatePersonController.updatePerson(request).getBody();
        Assert.assertNotNull(result);

         Assert.assertEquals(updatedComment, result.getPerson().getComment());
    }

}
