/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * User: christine
 * Date: 1/20/19 6:21 PM
 */
@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class LocationGetController {
    Logger logger = Logger.getLogger(LocationGetController.class);

    @Autowired
    private LocationService locationService;

    public LocationGetController() {
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_location/")
    public ResponseEntity<LocationResult> getLocation(@RequestBody LocationRequest request) throws IOException {

        LocationResult result = new LocationResult();

        try {

            MyLocation location = locationService.getLocation(request.getId());
            result.setLocation(location);
            result.setResult(LettersResult.OK);

        } catch (Exception e) {
            logger.error("Error getting location",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
