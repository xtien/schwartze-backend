/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.dao.LinkDao;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("linkService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    PersonDao personDao;

    @Override
    @Transactional("transactionManager")
    public MyLocation deleteLocationLink(EditLinkRequest request) {

        MyLocation location = null;

        if (request.getLocationId() != null) {
            location = locationDao.getLocation(request.getLocationId());
            location.getLinks().stream().filter(x -> x.getId() != request.getLinkId());
            linkDao.deleteLink(request.getLinkId());
        }
        return location;
    }

    @Override
    @Transactional("transactionManager")
    public Person deletePersonLink(EditLinkRequest request) {

        Person person = null;

        if (request.getPersonId() != null) {
            person = personDao.getPerson(request.getPersonId());
            person.getLinks().stream().filter(x -> x.getId() != request.getLinkId());
            linkDao.deleteLink(request.getLinkId());
        }
        return person;

    }
}
