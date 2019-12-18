/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.LettersFromPersonController;
import nl.christine.schwartze.server.controller.admin.ImportDBController;
import nl.christine.schwartze.server.controller.request.PersonLettersRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class TestLettersFromPersonController {

    @Autowired
    private LettersFromPersonController getLetterController;

    @Autowired
    private ImportDBController importDBController;

    private static LettersResult importResult;

    @Before
    public void before() {
        importResult = importDBController.importDB();
    }

    @Test
    public void testGetLetters() {

        PersonLettersRequest request = new PersonLettersRequest();
        request.setFromId(3);
        ResponseEntity<LettersResult> result = getLetterController.getLetters(request);

        Assert.assertNotNull(result);
        Assert.assertEquals(33, result.getBody().getLetters().size());
    }
}
