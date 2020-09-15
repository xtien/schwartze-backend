/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.result.LocationsResult;
import nl.christine.schwartze.server.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 7200)
public class LocationGetAllController {

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/get_locations/")
    @CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 7200)
    public ResponseEntity<LocationsResult> getLocations(@RequestBody LocationRequest request) throws IOException {

        LocationsResult locationsResult = new LocationsResult();
        locationsResult.setLocations(locationService.getAllLocations());
        return new ResponseEntity<>(locationsResult, HttpStatus.OK);
    }
}
