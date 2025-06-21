/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.*;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.CombinePersonResult;
import nl.christine.schwartze.server.controller.result.PeopleResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: christine
 * Date: 11/18/19 19:21 PM
 */
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Person", description = "")
public class AdminPersonController {

    Logger logger = LoggerFactory.getLogger(AdminPersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/updatePerson/")
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

    @PostMapping(value = "/deletePerson/")
    @Transactional("transactionManager")
    public ResponseEntity<PersonResult> deletePerson(@RequestBody DeletePersonRequest request) {

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

    @PostMapping(value = "/getCombinePerson/")
    public ResponseEntity<CombinePersonResult> getCombinePerson(@RequestBody CombinePersonRequest request) {
        return new ResponseEntity<>(personService.getCombinePersons(request.getId1(), request.getId2()), HttpStatus.OK);
    }

    @PostMapping(value = "/putCombinePerson/")
    public ResponseEntity<CombinePersonResult> putCombinePerson(@RequestBody CombinePersonRequest request) {
        return new ResponseEntity<>(personService.putCombinePersons(request.getId1(), request.getId2()), HttpStatus.OK);
    }

    @PostMapping(value = "/updatePersonDetails/")
    public ResponseEntity<PersonResult> updatePersonDetails(@RequestBody UpdatePersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(ApiResult.NOT_OK);

        try {
            Person updatedPerson = personService.updatePerson(request.getPerson());
            result.setResult(ApiResult.OK);
            result.setPerson(updatedPerson);
        } catch (Exception e) {
            logger.error("Error updating person", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getPersonsForIds/")
    public ResponseEntity<PeopleResult> getPersonsForIds(@RequestBody PeopleRequest request) {

        PeopleResult result = new PeopleResult();
         try {result.setPeople(personService.getPeople(request.getIds()));
             return new ResponseEntity<>(result, HttpStatus.OK);
         } catch (Exception e) {
            logger.error("get_people exception", e);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
