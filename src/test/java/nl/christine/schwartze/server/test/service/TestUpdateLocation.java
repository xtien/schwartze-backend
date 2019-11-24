/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.impl.LocationServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

/**
 * User: christine
 * Date: 12/29/18 12:28 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUpdateLocation {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationDao locationDao;

     private MyLocation location = new MyLocation();
     private int locationId = 12;

    @Test
    public void testLocation() {

        location.setId(locationId);
        when(locationDao.getLocation(locationId)).thenReturn(location);

        String newComment = "text text";
        location.setComment(newComment);

        MyLocation updatedLocation = locationService.updateLocationComment(location);

        Assert.assertNotNull(updatedLocation);
        Assert.assertEquals(newComment, updatedLocation.getComment());
    }

}

