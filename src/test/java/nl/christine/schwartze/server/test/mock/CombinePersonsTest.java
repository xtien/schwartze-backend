/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.PersonCombineController;
import nl.christine.schwartze.server.controller.request.CombinePersonRequest;
import nl.christine.schwartze.server.controller.result.CombinePersonResult;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonCombineController.class)
@ActiveProfiles("test")
public class CombinePersonsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private int personId1 = 12;
    private int personId2 = 22;
    private Person person1;
    private Person person2;
    private CombinePersonRequest request;
    private String firstName1 = "first name1";

    @Before
    public void setup(){

        request = new CombinePersonRequest();
        request.setId1(personId1);
        request.setId2(personId2);

        Link link1 = new Link();
        link1.setLinkName("link 1");
        Link link2 = new Link();
        link2.setLinkName("link 2");

        person1 = new Person();
        person1.setId(personId1);
        person1.setFirstName(firstName1);
        person1.addLinks(Collections.singletonList(link1));
        person2 = new Person();
        person2.setId(personId2);
        person2.addLinks(Collections.singletonList(link2));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testGetCombinePersons() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        CombinePersonResult result = new CombinePersonResult();
        result.setPerson1(person1);
        result.setPerson2(person2);

        when(personService.getCombinePersons(personId1, personId2)).thenReturn(result);

        String json = objectMapper.writeValueAsString(request);

        String responseString = this.mockMvc.perform(post("/admin/get_combine_person/")
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person1.id").value(personId1))
                .andExpect(jsonPath("$.person1.first_name").value(firstName1))
                .andExpect(jsonPath("$.person2.id").value(personId2))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CombinePersonResult response = objectMapper.readValue(responseString, CombinePersonResult.class);

        assertEquals(1,response.getPerson1().getLinks().size());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testPutCombinePersons() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        CombinePersonResult result = new CombinePersonResult();
        person1.getLinks().addAll(person2.getLinks());
        result.setPerson1(person1);

        when(personService.putCombinePersons(personId1, personId2)).thenReturn(result);

        String json = objectMapper.writeValueAsString(request);

        String responseString = this.mockMvc.perform(post("/admin/put_combine_person/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person1.id").value(personId1))
                .andExpect(jsonPath("$.person1.first_name").value(firstName1))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CombinePersonResult response = objectMapper.readValue(responseString, CombinePersonResult.class);

        assertEquals(2,response.getPerson1().getLinks().size());
    }
}
