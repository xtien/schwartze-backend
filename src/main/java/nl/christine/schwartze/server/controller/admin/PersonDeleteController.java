/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.DeletePersonRequest;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = Application.UI_HOST)
public class PersonDeleteController {

    Logger logger = Logger.getLogger(PersonDeleteController.class);

    @Autowired
    private PersonService personService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/delete_person/")
    @Transactional("transactionManager")
    public ResponseEntity<PersonResult> getPerson(@RequestBody DeletePersonRequest request) {

        PersonResult result = new PersonResult();
        HttpStatus status = HttpStatus.OK;

        try {
            int i = personService.deletePersonIfNoChildren(request.getPerson().getId());
            result.setResultCode(i);
        } catch (Exception e) {
            logger.error("Error getting person", e);
            result.setResultCode(-1);
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }
}
