/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.ReferenceController;
import nl.christine.schwartze.server.controller.request.PageRequest;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.ReferenceService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceController.class)
@ActiveProfiles("test")
public class TestGetReferencesController {

    private String pageNumber = "3";
    private String chapterNumber = "2";
    private String language = "nl";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PageService pageService;

    @MockitoBean
    private ReferenceService referenceService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testGetPage() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        when(pageService.getPage(language, pageNumber, chapterNumber));

        PageRequest request = new PageRequest();
        request.setPageNumber(pageNumber);

        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post("/getPageReferences/")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
        ;

    }
}
