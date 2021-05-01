/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.SubjectRequest;
import nl.christine.schwartze.server.controller.result.SubjectsResult;
import nl.christine.schwartze.server.service.SubjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class SubjectsGetController {

    Logger logger = Logger.getLogger(ReferenceController.class);

    @Value("${defaultlanguage}")
    private String defaultLanguage;

    @Autowired
    private SubjectService subjectService;

    @GetMapping(value = "/get_subjects/")
    public ResponseEntity<SubjectsResult> getSubjects() {

        SubjectsResult result = new SubjectsResult();

        try {
            result.setSubjects(subjectService.getSubjects(defaultLanguage));
        } catch (Exception e) {
            logger.error("Error getting references",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/get_subjects/")
    public ResponseEntity<SubjectsResult> getSubjects(@RequestBody SubjectRequest request) {

        SubjectsResult result = new SubjectsResult();

        try {
            result.setSubjects(subjectService.getSubjects(request.getLanguage()));
        } catch (Exception e) {
            logger.error("Error getting references",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
