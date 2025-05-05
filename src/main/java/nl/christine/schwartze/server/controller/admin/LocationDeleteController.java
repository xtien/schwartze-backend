/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.DeleteLocationRequest;
import nl.christine.schwartze.server.controller.result.DeleteLocationResult;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class LocationDeleteController {

    Logger logger = LoggerFactory.getLogger(LocationDeleteController.class);

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/delete_location/")
    public ResponseEntity<DeleteLocationResult> getPerson(@RequestBody DeleteLocationRequest request) {

        DeleteLocationResult result = new DeleteLocationResult();
        HttpStatus status = HttpStatus.OK;

        try {
            locationService.deleteLocation(request.getId());
        } catch (LocationNotFoundException e) {
            logger.error("Error getting person", e);
            status = HttpStatus.OK;
            result.setErrorText(e.getMessage());
        } catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.setErrorText(e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }
}
