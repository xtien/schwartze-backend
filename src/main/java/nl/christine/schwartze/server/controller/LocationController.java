/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.controller.result.LocationsResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: christine
 * Date: 1/20/19 6:21 PM
 */
@RestController
@Tag(name = "Location", description = "The letters API")
public class LocationController {
    Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    public LocationController() {
    }

    @PostMapping(value = "/getLocation/")
    public ResponseEntity<LocationResult> getLocation(@RequestBody LocationRequest request) {

        LocationResult result = new LocationResult();

        try {

            MyLocation location = locationService.getLocation(request.getId());
            result.setLocation(location);
            result.setResult(ApiResult.OK);

        } catch (Exception e) {
            logger.error("Error getting location", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getLocations/")
    public ResponseEntity<LocationsResult> getLocations(@RequestBody LocationRequest request) {

        LocationsResult locationsResult = new LocationsResult();
        locationsResult.setLocations(locationService.getAllLocations());
        return new ResponseEntity<>(locationsResult, HttpStatus.OK);
    }

}
