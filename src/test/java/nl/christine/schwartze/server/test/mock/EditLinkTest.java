/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.AdminLinkController;
import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LinkService;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@WebMvcTest(AdminLinkController.class)
@ActiveProfiles("test")
public class EditLinkTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @MockitoBean
    private LocationService locationService;

    @MockitoBean
    private LinkService linkService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private int personId = 123;
    private int textId = 3;
    private Integer locationId = 4;

    private List<Link> links;
    private String linkName1 = "example1";
    private String linkName2 = "example2";
    private String linkUrl = "http://example.com";

    @Before
    public void setup() {
        links = new ArrayList<>();
        links.add(new Link(linkName1, linkUrl));
        links.add(new Link(linkName2, linkUrl));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testAddPersonLink() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        EditLinkRequest editLinkRequest = new EditLinkRequest();
        editLinkRequest.setPersonId(personId);
        Person person = new Person();
        person.setLinks(links);
        person.setId(personId);

        String json = objectMapper.writeValueAsString(editLinkRequest);

        EditLinkResult editLinkResult = new EditLinkResult();
        editLinkResult.setPerson(person);
        when(personService.editLink(any(EditLinkRequest.class))).thenReturn(editLinkResult);

        this.mockMvc.perform(post("/admin/editLink/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person.links[0].link_name").value(linkName1));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testAddLocationLink() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        EditLinkRequest editLinkRequest = new EditLinkRequest();
        editLinkRequest.setLocationId(locationId);
        MyLocation location = new MyLocation();
        location.setId(locationId);
        location.setLinks(links);

        String json = objectMapper.writeValueAsString(editLinkRequest);

        EditLinkResult editLinkResult = new EditLinkResult();
        editLinkResult.setLocation(location);
        when(locationService.editLink(any(EditLinkRequest.class))).thenReturn(editLinkResult);

        this.mockMvc.perform(post("/admin/editLink/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                        .param(csrfToken.getParameterName(), csrfToken.getToken())
                        .characterEncoding("UTF-8")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location.links[0].link_name").value(linkName1));
    }
}
