/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.result.PeopleResult;
import nl.christine.schwartze.server.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class PersonGetAllController {

    @Autowired
    private PersonService locationService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_people/")
    public ResponseEntity<PeopleResult> getLocations(@RequestBody LocationRequest request) throws IOException {

        PeopleResult peopleResult = new PeopleResult();
        peopleResult.setPeople(locationService.getAllPeople());
        return new ResponseEntity<>(peopleResult, HttpStatus.OK);
    }
}
