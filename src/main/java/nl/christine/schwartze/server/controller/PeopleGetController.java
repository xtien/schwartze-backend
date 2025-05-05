/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.PeopleRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.PeopleResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PeopleGetController {

    Logger logger = LoggerFactory.getLogger(PeopleGetController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/get_people_details/")
    @Transactional("transactionManager")
    public ResponseEntity<PeopleResult> getPeople(@RequestBody PeopleRequest request) {

        PeopleResult result = new PeopleResult();
        result.setResult(ApiResult.NOT_OK);

        try {

            List<Person> people = personService.getPeople(request.getIds());
            if (people != null) {
                result.setPeople(people);
                result.setResultCode(ApiResult.OK);
            }
        } catch (Exception e) {
            logger.error("get_people_details exception", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
