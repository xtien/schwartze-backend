/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.CombineLocationResult;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
        return locationDao.addNewLocation(location);
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

        if (location == null) {
            return result;
        }

        if (request.getLinkId() == null) {
            Link link = new Link(request.getLinkName(), request.getLinkUrl());
            link.setLocation(location);
            location.getLinks().add(link);
        } else {
            Optional<Link> link = location.getLinks().stream().filter(x -> x.getId() == request.getLinkId()).findFirst();
            if (link.isPresent()) {
                link.get().setLinkName(request.getLinkName());
                link.get().setLinkUrl(request.getLinkUrl());
            }
        }

        result.setLocation(location);
        return result;
    }
    @Override
    @Transactional("transactionManager")
    public CombineLocationResult getCombineLocations(int id1, int id2) {

        CombineLocationResult result = new CombineLocationResult();
        result.setLocation1(getLocation(id1));
        result.setLocation2(getLocation(id2));
        return result;
    }

    @Override
    @Transactional("transactionManager")
    public CombineLocationResult putCombineLocations(int id1, int id2) {

        CombineLocationResult result = new CombineLocationResult();
        result.setLocation1(merge(getLocation(id1), getLocation(id2)));
        return result;
    }

    private MyLocation merge(MyLocation location1, MyLocation location2) {

        locationDao.merge(location1);
        locationDao.merge(location2);

        location2.getLettersFrom().stream().forEach(x -> {
            location1.getLettersFrom().add(x);
            x.addFromLocation(location1);
            x.removeFromLocation(location2);
        });

        location2.getLettersTo().stream().forEach(x -> {
            location1.getLettersTo().add(x);
            x.addToLocation(location1);
        });

        if(location2.getText() !=null && StringUtils.isNotEmpty(location2.getText().getTextString())){
            if(location1.getText() == null){
                location1.setText(location2.getText());
            } else {
                location1.getText().setTextString(location1.getText().getTextString() + " " + location2.getText().getTextString());
            }
        }
        if(StringUtils.isNotEmpty(location2.getComment())){
            location1.setComment(location1.getComment() + " " + location2.getComment());
        }

        if(StringUtils.isNotEmpty(location2.getDescription())){
            location1.setDescription(location1.getDescription() + " " + location2.getDescription());
        }

        if(!CollectionUtils.isEmpty(location2.getLinks())){
            if(location1.getLinks() == null){
                location1.setLinks(new ArrayList<>());
            }
            if(location2.getLinks() !=null){
                location1.getLinks().addAll(location2.getLinks());
            }
        }

        location2.getLinks().clear();
        location2.getLettersFrom().clear();
        location2.getLettersTo().clear();
        locationDao.deleteLocation(location2);

        return location1;
    }

    @Override
    public List<MyLocation> getAllLocations() {
        return locationDao.getLocations().stream().sorted(Comparator.comparing(MyLocation::getName)).collect(Collectors.toList());
    }
}
