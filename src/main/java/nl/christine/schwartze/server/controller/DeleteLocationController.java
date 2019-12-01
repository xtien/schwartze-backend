/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.DeleteLocationRequest;
import nl.christine.schwartze.server.controller.request.DeletePersonRequest;
import nl.christine.schwartze.server.controller.result.DeleteLocationResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class DeleteLocationController {

    Logger logger = Logger.getLogger(DeleteLocationController.class);

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
