/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.LetterUpdateController;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.impl.LetterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private LetterUpdateController letterUpdateController;

    @MockBean
    private LetterServiceImpl letterService;

    private int letterNumber = 12;
    private String comment = "testing";

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testUpdateLetter() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Letter letter = new Letter();
        letter.setNumber(letterNumber);
        letter.setComment(comment);

        when(letterService.updateLetterComment(letterNumber, comment)).thenReturn(letter);

        LetterRequest request = new LetterRequest();
        request.setComment(comment);
        request.setNumber(letterNumber);
        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post("/admin/update_letter_details/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.letter.comment").value(comment));
    }
}
