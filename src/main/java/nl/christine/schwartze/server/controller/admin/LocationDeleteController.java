/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.DeleteLocationRequest;
import nl.christine.schwartze.server.controller.result.DeleteLocationResult;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = Application.UI_HOST)
public class LocationDeleteController {

    Logger logger = Logger.getLogger(LocationDeleteController.class);

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/delete_location/")
    public ResponseEntity<DeleteLocationResult> getPerson(@RequestBody DeleteLocationRequest request) {

        DeleteLocationResult result = new DeleteLocationResult();
        HttpStatus status = HttpStatus.OK;

        try {
            locationService.deleteLocation(request.getLocation().getId());
        } catch (LocationNotFoundException e) {
            logger.error("Error getting person", e);
            status = HttpStatus.NOT_FOUND;
        } catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(result, status);
    }
}
