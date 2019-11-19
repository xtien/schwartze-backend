/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import nl.christine.schwartze.server.controller.UpdateLetterController;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.impl.LetterServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.mockito.Mockito.when;

/**
 * User: christine
 * Date: 12/29/18 12:17 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdateLetterController {

    @InjectMocks
    private UpdateLetterController updateLetterController;

    @Mock
    private LetterServiceImpl letterService;

    private int letterNumber = 12;
    private String comment = "testing";

    @Test
    public void testUpdateLetter()  {

        Letter letter = new Letter();
        letter.setNumber(letterNumber);
        letter.setComment(comment);
        when(letterService.updateLetterComment(letterNumber, comment)).thenReturn(letter);

        LetterRequest request = new LetterRequest();
        request.setComment(comment);
        request.setNumber(letterNumber);
        LetterResult result = updateLetterController.updateLetterComment(request).getBody();
        Assert.assertNotNull(result);

        Assert.assertEquals(request.getComment(), result.getLetter().getComment());
    }
}
