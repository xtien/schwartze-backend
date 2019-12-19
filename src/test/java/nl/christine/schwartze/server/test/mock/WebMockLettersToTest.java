/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.LettersToPersonController;
import nl.christine.schwartze.server.controller.request.PersonLettersRequest;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LetterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
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
@RunWith(SpringRunner.class)
@WebMvcTest(LettersToPersonController.class)
@ActiveProfiles("test")
public class WebMockLettersToTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LetterService letterService;

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
        letter1.setRecipient(person);
        letters.add(letter1);
        letters.add(new Letter());
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void greetingShouldReturnMessageFromService() throws Exception {

        letterRequest.setToId(3);
        String json = objectMapper.writeValueAsString(letterRequest);

        when(letterService.getLettersToPerson(3)).thenReturn(letters);

        this.mockMvc.perform(post("/get_letters_to_person/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.letters[0].recipients[0].first_name").value("Lizzy"));

        verify(letterService).getLettersToPerson(3);
    }
}