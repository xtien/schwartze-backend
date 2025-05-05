/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.CombineLocationRequest;
import nl.christine.schwartze.server.controller.result.CombineLocationResult;
import nl.christine.schwartze.server.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class LocationCombineController {

    @Autowired
    private LocationService locationService;

    @PostMapping(value = "/get_combine_location/")
    public ResponseEntity<CombineLocationResult> getCombinePerson(@RequestBody CombineLocationRequest request) {
        return new ResponseEntity<>(locationService.getCombineLocations(request.getId1(), request.getId2()), HttpStatus.OK);
    }

    @PostMapping(value = "/put_combine_location/")
    public ResponseEntity<CombineLocationResult> putCombinePerson(@RequestBody CombineLocationRequest request) {
        return new ResponseEntity<>(locationService.putCombineLocations(request.getId1(), request.getId2()), HttpStatus.OK);
    }
}
