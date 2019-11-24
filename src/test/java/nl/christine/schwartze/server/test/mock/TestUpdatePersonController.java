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
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
@WebMvcTest(UpdatePersonController.class)
public class TestUpdatePersonController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private int personId = 12;
    private String testComment = "just commenting ";

    @Test
    public void testUpdatePerson() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String updatedComment = "more commenting";

        Person p = new Person();
        p.setId(personId);
        p.setComment(testComment);
        Person updatedPerson = new Person();
        updatedPerson.setId(personId);
        updatedPerson.setComment(updatedComment);

        when(personService.updatePerson(any(Person.class))).thenReturn(updatedPerson);
        UpdatePersonRequest request = new UpdatePersonRequest();
        request.setPerson(p);

        String json = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post("/update_person_details/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person.id").value(personId))
                .andExpect(jsonPath("$.person.comment").value(updatedComment));

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(personService).updatePerson(personCaptor.capture());
        assertEquals(testComment, personCaptor.getValue().getComment());
    }
}
