/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.SearchPeopleRequest;
import nl.christine.schwartze.server.controller.result.PeopleResult;
import nl.christine.schwartze.server.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SearchPeopleController {

    Logger logger = LoggerFactory.getLogger(SearchPeopleController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/search_people/")
    public ResponseEntity<PeopleResult> searchPeople(@RequestBody SearchPeopleRequest request) {

        PeopleResult result = new PeopleResult();

        try {
            result.setPeople(personService.search(request.getSearchTerm()));
        } catch (Exception e) {
            logger.error("Error getting references",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
