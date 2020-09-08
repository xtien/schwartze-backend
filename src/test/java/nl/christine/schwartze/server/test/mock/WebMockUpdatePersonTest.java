/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.PersonUpdateController;
import nl.christine.schwartze.server.controller.request.UpdatePersonRequest;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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
@WebMvcTest(PersonUpdateController.class)
@ActiveProfiles("test")
public class WebMockUpdatePersonTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

    private List<Letter> letters = new LinkedList<>();
    private Letter letter1 = new Letter();

    private UpdatePersonRequest request = new UpdatePersonRequest();

    private String comment = "this my comment";
    private ObjectMapper objectMapper;
    private Person person;

    @Before
    public void setup() {
        letter1.setComment(comment);
        person = new Person();
        person.setId(3);
        person.setName("Lizzy");
        letter1.setRecipient(person);
        letters.add(letter1);
        letters.add(new Letter());
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void greetingShouldReturnMessageFromService() throws Exception {

        request.setPerson(person);
        String json = objectMapper.writeValueAsString(request);

        when(personService.updatePerson(person)).thenReturn(person);

        this.mockMvc.perform(post("/admin/update_person_details/")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0));

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).updatePerson(captor.capture());
        assertThat(captor.getValue().getName(), is(equalTo(person.getName())));
    }
}
