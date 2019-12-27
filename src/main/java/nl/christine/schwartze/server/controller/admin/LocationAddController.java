/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.AddLocationRequest;
import nl.christine.schwartze.server.controller.result.AddLocationResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
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
public class LocationAddController {

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/add_location/")
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
}
