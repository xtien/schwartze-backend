/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.controller.result.TextResult;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class TextUpdateController {

    Logger logger = LoggerFactory.getLogger(TextUpdateController.class);

    @Autowired
    private TextService textService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private LetterService letterService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/update_text/")
    public ResponseEntity<TextResult> updateText(@RequestBody TextRequest request) {

        TextResult result = new TextResult();
        HttpStatus status = HttpStatus.OK;

        try {
            Text text = textService.updateText(request);

            if (request.getPersonId() != null) {
                result.setPerson(personService.getPerson(request.getPersonId()));
            }
            if (request.getLocationId() != null) {
                result.setLocation(locationService.getLocation(request.getLocationId()));
            }
            if (request.getLetterId() != null) {
                result.setLetter(letterService.getLetterById(request.getLetterId()));
            }
            if (request.getSubjectId() != null) {
                result.setSubject(subjectService.getSubjectById(request.getSubjectId(), request.getLanguage()));
            }
            if (text != null) {
                result.setText(text);

            } else {
                status = HttpStatus.NOT_FOUND;
            }

        } catch (Exception e) {
            logger.error("edit_text exception ", e);
        }

        return new ResponseEntity<>(result, status);
    }
}
