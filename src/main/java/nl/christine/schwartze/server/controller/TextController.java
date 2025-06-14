/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.controller.result.TextResult;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import nl.christine.schwartze.server.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Text", description = "")
public class TextController {

    Logger logger = LoggerFactory.getLogger(TextController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LetterService letterService;

    @Autowired
    private SubjectService subjectService;

    @PostMapping(value = "/getText/")
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
                result.setSubject(subjectService.getSubjectById(request.getSubjectId(), request.getLanguage()));
            }
        } catch (Exception e) {
            logger.error("create_text exception ", e);
            //result.setErrorText(e.getClass().getCanonicalName());
            result.setErrorText("EROR: text not found");
        }

        return new ResponseEntity<>(result, status);
    }
}
