/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.impl.LetterServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.mockito.Mockito.when;

/**
 * User: christine
 * Date: 12/29/18 12:02 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdateLetterComment {

    @InjectMocks
    private LetterServiceImpl letterService;

    @Mock
    private LetterDao letterDao;

    private String comment = "test";
    private String newComment = "testtest";
    private String newDate = "12121912";

    @Test
    public void testGetLetter() throws IOException {

        Letter letter = new Letter();
        letter.setNumber(12);
        letter.setComment(comment);
        Letter newLetter = new Letter();
        newLetter.setNumber(12);
        newLetter.setComment(newComment);

        when(letterDao.getLetterForNumber(letter.getNumber())).thenReturn(letter);
        when(letterDao.updateLetterComment(letter.getNumber(), newComment, newDate)).thenReturn(newLetter);

        Letter resultingLetter =   letterService.updateLetterComment(12, newComment, newDate);

        Assert.assertNotNull(resultingLetter);
        Assert.assertEquals(newComment, resultingLetter.getComment());
    }
}
