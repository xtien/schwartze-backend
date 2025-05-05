/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.DeletePersonRequest;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class PersonDeleteController {

    Logger logger = LoggerFactory.getLogger(PersonDeleteController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/delete_person/")
    @Transactional("transactionManager")
    public ResponseEntity<PersonResult> getPerson(@RequestBody DeletePersonRequest request) {

        PersonResult result = new PersonResult();
        HttpStatus status = HttpStatus.OK;

        int personId = 0;
        try {
            if (request.getPerson() != null) {
                personId = request.getPerson().getId();
            } else if (request.getPersonId() != null) {
                personId = request.getPersonId();
            }
            if (personId != 0) {
                personService.deletePersonIfNoChildren(personId);
            }
        } catch (Exception e) {
            logger.error("Error getting person", e);
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }
}
