/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.GetPersonRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PersonGetController {

    Logger logger = LoggerFactory.getLogger(PersonGetController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/get_person_details/")
    public ResponseEntity<PersonResult> getPerson(@RequestBody GetPersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(ApiResult.NOT_OK);

        try {

            Person person = personService.getPerson(request.getId());
            if (person != null) {
                result.setPerson(person);
                result.setResultCode(ApiResult.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting person",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
