/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.pub;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.PeopleRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.controller.result.PeopleResult;
import nl.christine.schwartze.server.model.Person;
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

import java.util.List;

@Controller
@RequestMapping("/pub")
@CrossOrigin(origins = Application.UI_HOST)
public class PeopleGetController {

    Logger logger = Logger.getLogger(PeopleGetController.class);

    @Autowired
    private PersonService personService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_people_details/")
    @Transactional("transactionManager")
    public ResponseEntity<PeopleResult> getPeople(@RequestBody PeopleRequest request) {

        PeopleResult result = new PeopleResult();
        result.setResult(LettersResult.NOT_OK);

        try {

            List<Person> people = personService.getPeople(request.getIds());
            if (people != null) {
                result.setPeople(people);
                result.setResultCode(LettersResult.OK);
            }
        } catch (Exception e) {
            logger.error("get_people_details exception", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
