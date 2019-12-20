/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.dao.LinkDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LinkService;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestDeleteLink {

    @Autowired
    private PersonService personService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private LocationService locationService;

    @Test
    public void testDeletePersonLink(){

         Person person = new Person();
        person.setFirstName("Lizzy");
        Person resultPerson = personService.addPerson(person);
        int personId = resultPerson.getId();
        EditLinkRequest editLinkRequest = new EditLinkRequest();
        editLinkRequest.setPersonId(personId);
        editLinkRequest.setLinkName("wikipedia");
        personService.editLink(editLinkRequest);

         Person p3 = personService.getPerson(personId);
        assertEquals(1, p3.getLinks().size());
        int linkId = p3.getLinks().get(0).getId();

        EditLinkRequest request = new EditLinkRequest();
        request.setLinkId(linkId);
        request.setPersonId(personId);

        Person p4 = linkService.deletePersonLink(request);

        assertEquals(0, p4.getLinks().size());
    }

    @Test
    public void testDeleteLocationLink(){

        MyLocation location = new MyLocation();
        location.setName("Fischhausen");
        MyLocation resultMyLocation = locationService.addLocation(location);
        int locationId = resultMyLocation.getId();
        EditLinkRequest editLinkRequest = new EditLinkRequest();
        editLinkRequest.setLocationId(locationId);
        editLinkRequest.setLinkName("wikipedia");
        locationService.editLink(editLinkRequest);

        MyLocation p3 = locationService.getLocation(locationId);
        assertEquals(1, p3.getLinks().size());
        int linkId = p3.getLinks().get(0).getId();

        EditLinkRequest request = new EditLinkRequest();
        request.setLinkId(linkId);
        request.setLocationId(locationId);

        MyLocation p4 =  linkService.deleteLocationLink(request);

        assertEquals(0, p4.getLinks().size());
    }

}
