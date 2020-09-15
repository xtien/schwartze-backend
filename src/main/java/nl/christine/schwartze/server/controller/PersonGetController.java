/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.GetPersonRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 7200)
public class PersonGetController {

    Logger logger = Logger.getLogger(PersonGetController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/get_person_details/")
    public ResponseEntity<PersonResult> getPerson(@RequestBody GetPersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(LettersResult.NOT_OK);

        try {

            Person person = personService.getPerson(request.getId());
            if (person != null) {
                result.setPerson(person);
                result.setResultCode(LettersResult.OK);
            }
        } catch (Exception e) {
            logger.error("Error getting person",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
