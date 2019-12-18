/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.UpdatePersonRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = Application.UI_HOST)
public class PersonUpdateController {

    Logger logger = Logger.getLogger(PersonUpdateController.class);

    @Autowired
    private PersonService personService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/update_person_details/")
    public ResponseEntity<PersonResult> updatePerson(@RequestBody UpdatePersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(PersonResult.NOT_OK);

        try {
            Person updatedPerson = personService.updatePerson(request.getPerson());
            result.setResult(PersonResult.OK);
            result.setPerson(updatedPerson);
        } catch (Exception e) {
            logger.error("Error updating person",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
