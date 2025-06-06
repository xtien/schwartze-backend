/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.*;
import nl.christine.schwartze.server.controller.result.*;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Location", description = "")
public class AdminLocationController {

    Logger logger = LoggerFactory.getLogger(AdminLocationController.class);

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/addLocation/")
    public ResponseEntity<AddLocationResult> addLocation(@RequestBody AddLocationRequest request) {

        AddLocationResult result = new AddLocationResult();

        if (request.getLocation().getId() > 0) {
            result.setText("location exists");
        } else {
            MyLocation resultLocation = locationService.addLocation(request.getLocation());
            result.setLocation(resultLocation);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping(value = "/getCombineLocation/")
    public ResponseEntity<CombineLocationResult> getCombineLocation(@RequestBody CombineLocationRequest request) {
        return new ResponseEntity<>(locationService.getCombineLocations(request.getId1(), request.getId2()), HttpStatus.OK);
    }

    @PostMapping(value = "/putCombineLocation/")
    public ResponseEntity<CombineLocationResult> putCombineLocation(@RequestBody CombineLocationRequest request) {
        return new ResponseEntity<>(locationService.putCombineLocations(request.getId1(), request.getId2()), HttpStatus.OK);
    }

    @PostMapping(value = "/deleteLocation/")
    public ResponseEntity<DeleteLocationResult> deleteLocation(@RequestBody DeleteLocationRequest request) {

        DeleteLocationResult result = new DeleteLocationResult();
        HttpStatus status = HttpStatus.OK;

        try {
            locationService.deleteLocation(request.getId());
        } catch (LocationNotFoundException e) {
            logger.error("Error getting person", e);
            status = HttpStatus.OK;
            result.setErrorText(e.getMessage());
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.setErrorText(e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "/updateLocationDetails/")
    @Transactional("transactionManager")
    public ResponseEntity<LocationResult> updateLocation(@RequestBody UpdateLocationRequest request) {

        LocationResult result = new LocationResult();
        result.setResult(ApiResult.NOT_OK);

        try {
            MyLocation resultLocation = locationService.updateLocationComment(request.getLocation());
            result.setLocation(resultLocation);
        } catch (Exception e) {
            logger.error("Error updating location", e);
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

    @PostMapping(value = "/getLocationsForIds/")
    public ResponseEntity<LocationsResult> getLocationsForIds(@RequestBody LocationsRequest request) {

        try {
            LocationsResult locationsResult = new LocationsResult();
            locationsResult.setLocations(locationService.getLocations(request.getIds()));
            return new ResponseEntity<>(locationsResult, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("get_locations exception", e);
        }
        return new ResponseEntity<>(new LocationsResult(), HttpStatus.NOT_FOUND);
    }
}
