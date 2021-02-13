/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.controller.result.CombineLocationResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
public class TestCombineLocation {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LetterService letterservice;


    @Test
    public void testCombine() {

        MyLocation location1 = new MyLocation();
        location1.setName("Amsterdam");

        MyLocation location2 = new MyLocation();
        location2.setName("Ammsterdam");

        MyLocation location3 = new MyLocation();
        location3.setName("Mainz");

        Letter letter1 = new Letter();
        letter1.setComment("comment 1");
        letter1.setNumber(1);
        letter1.addFromLocation(location1);
        letter1.addToLocation(location3);
        location1.addLetterFrom(letter1);
        location3.addLetterTo(letter1);

        Letter letter2 = new Letter();
        letter2.setNumber(2);
        letter2.setComment("comment 2");
        letter2.addFromLocation(location2);
        letter2.addToLocation(location3);
        location2.addLetterFrom(letter2);
        location3.addLetterTo(letter2);

        Letter letter3 = new Letter();
        letter3.setNumber(3);
        letter3.setComment("comment 3");
        letter3.addFromLocation(location3);
        letter3.addToLocation(location1);
        location3.addLetterFrom(letter3);
        location1.addLetterTo(letter3);

        Letter letter4 = new Letter();
        letter4.setNumber(4);
        letter4.setComment("comment 4");
        letter4.addFromLocation(location3);
        letter4.addToLocation(location2);
        location3.addLetterFrom(letter4);
        location2.addLetterTo(letter4);

        locationService.addLocation(location1);

        CombineLocationResult result = locationService.getCombineLocations(location1.getId(), location2.getId());

        assertEquals("Amsterdam", result.getLocation1().getName());
        assertEquals("Ammsterdam", result.getLocation2().getName());

        CombineLocationResult result2 = locationService.putCombineLocations(location1.getId(), location2.getId());

        assertEquals("Amsterdam", result2.getLocation1().getName());

        List<Letter> lettersFrom = letterservice.getLettersForLocation(location1.getId());
        assertEquals(4, lettersFrom.size());
    }
}
