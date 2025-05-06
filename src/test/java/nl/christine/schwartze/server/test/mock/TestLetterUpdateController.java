/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.LetterUpdateCommentController;
import nl.christine.schwartze.server.controller.admin.LetterUpdateController;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.impl.LetterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: christine
 * Date: 12/29/18 12:17 PM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LetterUpdateController.class)
@ActiveProfiles("test")
public class TestLetterUpdateController {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private LetterUpdateCommentController letterUpdateCommentController;

    @MockBean
    private LetterServiceImpl letterService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private int letterNumber = 12;
    private String comment = "testing";
    private String date = "12121912";

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testUpdateLetter() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Letter letter = createLetter(1);
        Letter newLetter = new Letter();
        newLetter.setId(letter.getId());
        newLetter.setComment(letter.getComment());
        newLetter.setNumber(letter.getNumber());
        newLetter.getRecipients().add(letter.getRecipients().get(0));
        newLetter.getSenders().add(letter.getSenders().get(0));
        newLetter.getSenders().add(createPerson(2, 5));
        newLetter.getRecipients().add(createPerson(3, 6));

        when(letterService.updateLetter(any(Letter.class))).thenReturn(newLetter);

        LetterRequest request = new LetterRequest();
        request.setComment(comment);
        request.setNumber(letterNumber);
        request.setDate(date);
        request.setLetter(newLetter);
        String json = objectMapper.writeValueAsString(request);

        MockHttpServletResponse response = this.mockMvc.perform(post("/admin/update_letter/")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        Letter resultLetter = objectMapper.readValue(response.getContentAsString(), LetterResult.class).getLetter();
        assertEquals(resultLetter.getSenders().get(0).toString(), newLetter.getSenders().get(0).toString());
        assertEquals(resultLetter.getSenders().get(1).toString(), newLetter.getSenders().get(1).toString());
    }

    private Letter createLetter(int i) {
        Letter letter = new Letter();
        letter.setNumber(i);
        letter.setId(i);
        letter.setComment(comment + i);
        letter.getSenders().add(createPerson(i, 1));
        letter.getSenders().add(createPerson(i, 2));
        letter.getRecipients().add(createPerson(i, 3));
        letter.getRecipients().add(createPerson(i, 4));
        return letter;
    }

    private Person createPerson(int i, int j) {
        Person person = new Person();
        person.setId(i * 10 + j);
        person.setFirstName("first" + i + j);
        person.setLastName("last" + i + j);
        return person;
    }
}
