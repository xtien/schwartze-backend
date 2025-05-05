/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.AddPersonRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: christine
 * Date: 11/18/19 19:21 PM
 */
@RestController
@RequestMapping("/admin")
public class PersonAddController {

    Logger logger = LoggerFactory.getLogger(PersonAddController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/add_person/")
    public ResponseEntity<PersonResult> updatePerson(@RequestBody AddPersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(ApiResult.NOT_OK);

        try {
            Person updatedPerson = personService.addPerson(request.getPerson());
            result.setResult(ApiResult.OK);
            result.setPerson(updatedPerson);
        } catch (Exception e) {
            logger.error("Error updating person", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
