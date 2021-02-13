/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.UpdatePageController;
import nl.christine.schwartze.server.controller.request.PageRequest;
import nl.christine.schwartze.server.controller.result.PageResult;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.impl.PageServiceImpl;
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UpdatePageController.class)
@ActiveProfiles("test")
public class TestPageController {

    String pageNumber = "11";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PageService pageService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void addPage() throws Exception {

        PageRequest request = new PageRequest();
        request.setPageNumber(pageNumber);
        String json = objectMapper.writeValueAsString(request);
        when(pageService.getPage(pageNumber)).thenReturn(new Page());

        MockHttpServletResponse response = this.mockMvc.perform(post("/admin/add_page/")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        PageResult result = objectMapper.readValue(response.getContentAsString(), PageResult.class);

        assertNotNull(result);
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void removePage() throws Exception {

        PageRequest request = new PageRequest();
        request.setPageNumber(pageNumber);
        String json = objectMapper.writeValueAsString(request);

        when(pageService.getPage(pageNumber)).thenReturn(new Page());

        MockHttpServletResponse response = this.mockMvc.perform(post("/admin/remove_page/")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        PageResult result = objectMapper.readValue(response.getContentAsString(), PageResult.class);

        assertNotNull(result);
    }


    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void addReference() throws Exception {

        PageRequest request = new PageRequest();
        request.setPageNumber(pageNumber);
        String json = objectMapper.writeValueAsString(request);

        when(pageService.getPage(pageNumber)).thenReturn(new Page());

        MockHttpServletResponse response = this.mockMvc.perform(post("/admin/add_page_reference/")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();

        PageResult result = objectMapper.readValue(response.getContentAsString(), PageResult.class);

        assertNotNull(result);
    }
}
