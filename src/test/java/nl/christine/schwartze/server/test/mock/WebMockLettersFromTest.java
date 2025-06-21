/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.LetterController;
import nl.christine.schwartze.server.controller.enums.LettersOrderByEnum;
import nl.christine.schwartze.server.controller.request.PersonLettersRequest;
import nl.christine.schwartze.server.controller.request.ToFrom;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.TextService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: christine
 * Date: 1/21/19 2:32 PM
 */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@WebMvcTest(LetterController.class)
@ActiveProfiles("test")
public class WebMockLettersFromTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LetterService letterService;

    @MockitoBean
    private TextService textService;

    @MockitoBean
    private SchwartzeProperties schwartzeProperties;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private List<Letter> letters = new LinkedList<>();
    private Letter letter1 = new Letter();

    private PersonLettersRequest letterRequest = new PersonLettersRequest();

    private String comment = "this my comment";
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        letter1.setComment(comment);
        Person person = new Person();
        person.setId(3);
        person.setFirstName("Lizzy");
        letter1.setSender(person);
        letters.add(letter1);
        letters.add(new Letter());
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void greetingShouldReturnMessageFromService() throws Exception {

        letterRequest.setId(3);
        letterRequest.setOrderBy(LettersOrderByEnum.DATE);
        letterRequest.setToFrom(ToFrom.TO);
        String json = objectMapper.writeValueAsString(letterRequest);

        when(letterService.getLettersForPerson(3, LettersOrderByEnum.DATE)).thenReturn(letters);
        when(schwartzeProperties.getProperty("letters_directory")).thenReturn("src/test/resources/letters");
        when(schwartzeProperties.getProperty("text_document_name")).thenReturn("test.txt");

        this.mockMvc.perform(post("/getLettersForPerson/")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.letters[0].senders[0].nick_name").value("Lizzy"));

        verify(letterService).getLettersForPerson(3, LettersOrderByEnum.DATE);
    }
}
