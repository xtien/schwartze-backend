/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.SubjectRequest;
import nl.christine.schwartze.server.controller.result.SubjectsResult;
import nl.christine.schwartze.server.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class SubjectAddController {

    Logger logger = LoggerFactory.getLogger(SubjectAddController.class);

    @Autowired
    private SubjectService subjectService;

    @PostMapping(value = "/add_subject/")
    public ResponseEntity<SubjectsResult> addSubject(@RequestBody SubjectRequest request) {

        SubjectsResult result = new SubjectsResult();

        try {
            result.setSubjects(subjectService.addSubject(request.getSubjectName(), request.getLanguage()));
        } catch (Exception e) {
            logger.error("Error getting references", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
