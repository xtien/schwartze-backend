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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class SubjectRemoveController {

    @Autowired
    private SubjectService subjectService;

    Logger logger = Logger.getLogger(SubjectRemoveController.class);

    @PostMapping(value = "/remove_subject/")
    public ResponseEntity<SubjectsResult> updateSubject(@RequestBody SubjectRequest request) {

        SubjectsResult result = new SubjectsResult();

        try {
            result.setSubjects(subjectService.removeSubject(request.getSubjectId(), request.getLanguage()));
        } catch (Exception e) {
            logger.error("Error getting references", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
