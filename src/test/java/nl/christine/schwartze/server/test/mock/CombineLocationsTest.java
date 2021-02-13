/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.christine.schwartze.server.controller.admin.LocationCombineController;
import nl.christine.schwartze.server.controller.request.CombineLocationRequest;
import nl.christine.schwartze.server.controller.result.CombineLocationResult;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationCombineController.class)
@ActiveProfiles("test")
public class CombineLocationsTest {

    private MockMvc mockMvc;

    @MockBean
    private LocationService LocationService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private int locationId1 = 12;
    private int locationId2 = 22;
    private MyLocation location1;
    private MyLocation location2;
    private CombineLocationRequest request;
    private String name1 = "first name1";

    @Before
    public void setup(){

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        request = new CombineLocationRequest();
        request.setId1(locationId1);
        request.setId2(locationId2);

        Link link1 = new Link();
        link1.setLinkName("link 1");
        Link link2 = new Link();
        link2.setLinkName("link 2");

        location1 = new MyLocation();
        location1.setId(locationId1);
        location1.setName(name1);
        location1.addLinks(Collections.singletonList(link1));
        location2 = new MyLocation();
        location2.setId(locationId2);
        location2.addLinks(Collections.singletonList(link2));
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testGetCombineLocations() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        CombineLocationResult result = new CombineLocationResult();
        result.setLocation1(location1);
        result.setLocation2(location2);

        when(LocationService.getCombineLocations(locationId1, locationId2)).thenReturn(result);

        String json = objectMapper.writeValueAsString(request);

        String responseString = this.mockMvc.perform(post("/admin/get_combine_location/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(csrf())
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location1.id").value(locationId1))
                .andExpect(jsonPath("$.location1.name").value(name1))
                .andExpect(jsonPath("$.location2.id").value(locationId2))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CombineLocationResult response = objectMapper.readValue(responseString, CombineLocationResult.class);

        assertEquals(1,response.getLocation1().getLinks().size());
    }

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testPutCombineLocations() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        CombineLocationResult result = new CombineLocationResult();
        location1.getLinks().addAll(location2.getLinks());
        result.setLocation1(location1);

        when(LocationService.putCombineLocations(locationId1, locationId2)).thenReturn(result);

        String json = objectMapper.writeValueAsString(request);

        String responseString = this.mockMvc.perform(post("/admin/put_combine_location/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(csrf())
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location1.id").value(locationId1))
                .andExpect(jsonPath("$.location1.name").value(name1))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CombineLocationResult response = objectMapper.readValue(responseString, CombineLocationResult.class);

        assertEquals(2,response.getLocation1().getLinks().size());
    }
}
