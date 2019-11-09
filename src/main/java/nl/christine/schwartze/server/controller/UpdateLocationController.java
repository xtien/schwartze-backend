/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.request.UpdateLocationRequest;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UpdateLocationController {

    Logger logger = Logger.getLogger(UpdateLocationController.class);

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/update_location_details/")
    @Transactional("transactionManager")
    public ResponseEntity<LocationResult> updateLocation(@RequestBody UpdateLocationRequest request) {

        LocationResult result = new LocationResult();
        result.setResult(PersonResult.NOT_OK);

        try {
            MyLocation location = locationService.getLocation(request.getLocation().getId());
            if (location != null) {
                location.setComment(request.getLocation().getComment());
                location.setDescription(request.getLocation().getDescription());
                result.setResultCode(LocationResult.OK);
            }
        } catch (Exception e) {
            logger.error("Error updating location",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
