/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.AdminTextController;
import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.dao.TextDao;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.*;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@WebMvcTest(AdminTextController.class)
@ActiveProfiles("test")
public class UpdateTextTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private TextDao textDao;

    @MockitoBean
    private PersonDao personDao;

    @MockitoBean
    private TextService textService;

    @MockitoBean
    private LocationService locationService;

    @MockitoBean
    private LetterService letterService;

    @MockitoBean
    private SubjectService subjectService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private int personId = 123;
    private Text text;
    private int textId = 3;
    private Integer locationId = 4;
    private String textString = "string text string";

    @Before
    public void setup() {
        text = new Text();
        text.setId(textId);
        text.setTextString(textString);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testEditText() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        TextRequest textRequest = new TextRequest();
        text.setTextString(textString);
        textRequest.setText(text);

        String json = objectMapper.writeValueAsString(textRequest);

        when(textService.updateText(any(TextRequest.class))).thenReturn(text);

        this.mockMvc.perform(post("/admin/updateText/")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text.text_string").value(textString));
    }
}
