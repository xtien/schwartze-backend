/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.LocationUpdateRequest;
import nl.christine.schwartze.server.controller.request.UpdateLocationRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class LocationUpdateController {

    Logger logger = LoggerFactory.getLogger(LocationUpdateController.class);

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/update_location_details/")
    @Transactional("transactionManager")
    public ResponseEntity<LocationResult> updateLocation(@RequestBody UpdateLocationRequest request) {

        LocationResult result = new LocationResult();
        result.setResult(ApiResult.NOT_OK);

        try {
            MyLocation resultLocation  = locationService.updateLocationComment(request.getLocation());
            result.setLocation(resultLocation);
        } catch (Exception e) {
            logger.error("Error updating location",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/update_location/")
    public ResponseEntity<LocationResult> updateLocation(@RequestBody LocationUpdateRequest request) {

        LocationResult result = new LocationResult();

        try {

            MyLocation location = locationService.updateLocation(request.getId(), request.getName(), request.getComment());
            result.setLocation(location);
            result.setResult(ApiResult.OK);

        } catch (Exception e) {
            logger.error("Error getting location", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
