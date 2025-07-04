/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.mock;

import nl.christine.schwartze.server.controller.admin.AdminLocationController;
import nl.christine.schwartze.server.controller.request.UpdateLocationRequest;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.mockito.Mockito.when;

/**
 * User: christine
 * Date: 12/29/18 12:17 PM
 */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestLocationUpdateController {

    @InjectMocks
    private AdminLocationController locationUpdateController;

    @Mock
    private LocationService locationService;

    private int locationId = 12;
    private String locationName = "Hilversum";
    private String testDescription = "just testing";
    private String testComment = "just commenting";

    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testUpdateLocation() throws IOException {

        UpdateLocationRequest request = new UpdateLocationRequest();
        MyLocation myLocation = new MyLocation();
        myLocation.setDescription(testDescription);
        myLocation.setComment(testComment);
        myLocation.setId(locationId);
        request.setLocation(myLocation);

        when(locationService.updateLocationComment(myLocation)).thenReturn(myLocation);

        LocationResult result = locationUpdateController.updateLocation(request).getBody();

        Assert.assertNotNull(result);

        Assert.assertEquals(testDescription, result.getLocation().getDescription());
        Assert.assertEquals(testComment, result.getLocation().getComment());
    }

}
