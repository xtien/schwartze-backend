/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.controller.result.TextResult;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import nl.christine.schwartze.server.service.SubjectService;
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
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class LetterTextGetController {

    Logger logger = Logger.getLogger(LetterTextGetController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LetterService letterService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping(value = "/get_text/")
    public ResponseEntity<TextResult> getText(@RequestBody TextRequest request) {

        TextResult result = new TextResult();
        HttpStatus status = HttpStatus.OK;

        try {
            if (request.getPersonId() != null) {
                result.setText(personService.getText(request.getPersonId()));
                result.setPerson(personService.getPerson(request.getPersonId()));
            } else if (request.getLocationId() != null) {
                result.setText(locationService.getText(request.getLocationId()));
                result.setLocation(locationService.getLocation(request.getLocationId()));
            } else if (request.getLetterId() != null) {
                result.setText(letterService.getText(request.getLetterId()));
                result.setLetter(letterService.getLetterById(request.getLetterId()));
            } else if (request.getSubjectId() != null) {
                result.setSubject(subjectService.getSubjectById(request.getSubjectId()));
            }
        } catch (Exception e) {
            logger.error("create_text exception ", e);
            result.setErrorText(e.getClass().getCanonicalName());
        }

        return new ResponseEntity<>(result, status);
    }
}
