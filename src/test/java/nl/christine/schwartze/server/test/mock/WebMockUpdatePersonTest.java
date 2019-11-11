/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.UpdatePersonController;
import nl.christine.schwartze.server.controller.request.UpdatePersonRequest;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
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
@WebMvcTest(UpdatePersonController.class)
public class WebMockUpdatePersonTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

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
        person.setFirstName("Lizzy");
        letter1.setRecipient(person);
        letters.add(letter1);
        letters.add(new Letter());
        objectMapper = new ObjectMapper();
    }

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {

        request.setId(3);
        request.setPerson(person);
        String json = objectMapper.writeValueAsString(request);

        when(personService.updatePerson(person)).thenReturn(0);

        this.mockMvc.perform(post("/update_person_details/")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(0));

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personService).updatePerson(captor.capture());
        assertThat(captor.getValue().getFirstName(), is(equalTo(person.getFirstName())));
    }
}