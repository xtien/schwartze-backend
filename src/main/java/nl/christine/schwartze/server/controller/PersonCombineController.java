/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.CombinePersonRequest;
import nl.christine.schwartze.server.controller.result.CombinePersonResult;
import nl.christine.schwartze.server.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class PersonCombineController {

    @Autowired
    private PersonService personService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_combine_person/")
    public ResponseEntity<CombinePersonResult> getCombinePerson(@RequestBody CombinePersonRequest request) {
        return new ResponseEntity<>(personService.getCombinePersons(request.getId1(), request.getId2()), HttpStatus.OK);
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/put_combine_person/")
    public ResponseEntity<CombinePersonResult> putCombinePerson(@RequestBody CombinePersonRequest request) {
        return new ResponseEntity<>(personService.putCombinePersons(request.getId1(), request.getId2()), HttpStatus.OK);
    }
}
