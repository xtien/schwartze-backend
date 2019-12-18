/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.TextUpdateController;
import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import nl.christine.schwartze.server.service.TextService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TextUpdateController.class)
public class UpdateTextTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private TextService textService;

    @MockBean
    private LocationService locationService;
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
    public void testEditText() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        TextRequest textRequest = new TextRequest();
        text.setTextString(textString);
        textRequest.setText(text);

        String json = objectMapper.writeValueAsString(textRequest);

        when(textService.updateText(any(TextRequest.class))).thenReturn(text);

        this.mockMvc.perform(post("/update_text/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text.textString").value(textString));
    }
}
