/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.TextDao;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: christine
 * Date: 12/29/18 12:26 PM
 */
@Component("locationService")
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Override
    @Transactional("transactionManager")
    public MyLocation updateLocationComment(MyLocation location) {
        MyLocation existingLocation = null;

        try {
            existingLocation = locationDao.getLocation(location.getId());
            existingLocation.setComment(location.getComment());
        } catch (Exception e) {
            logger.error("Error updating location comment", e);
        }

        return existingLocation;
    }

    @Override
    @Transactional("transactionManager")
    public MyLocation getLocation(int i) {
        return locationDao.getLocation(i);
    }

    @Override
    @Transactional("transactionManager")
    public MyLocation addLocation(MyLocation location) {
        return locationDao.addLocation(location);
    }

    @Override
    @Transactional("transactionManager")
    public void deleteLocation(int id) throws LocationNotFoundException {
        locationDao.deleteLocation(id);
    }

    @Override
    @Transactional("transactionManager")
    public Text getText(int id) {
        return locationDao.getLocationText(id);
    }

    @Override
    @Transactional("transactionManager")
    public EditLinkResult editLink(EditLinkRequest request) {

        EditLinkResult result = new EditLinkResult();
        MyLocation location = getLocation(request.getLocationId());

        if(location == null){
            return result;
        }

        if(request.getLinkId() == null){
            location.getLinks().add(new Link(request.getLinkName(), request.getLinkUrl()));
        } else {
            location.getLinks().stream().filter(x -> x.getId() == request.getLinkId()).map( x -> {
                x.setLinkName(request.getLinkName());
                x.setLinkUrl(request.getLinkUrl());
                return x;
            });
        }

        result.setLocation(location);
        return result;
    }
}
