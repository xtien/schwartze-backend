/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.ReferenceController;
import nl.christine.schwartze.server.controller.request.SubjectRequest;
import nl.christine.schwartze.server.controller.result.SubjectsResult;
import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.service.SubjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = Application.UI_HOST, maxAge = 14400)
public class SubjectUpdateController {   Logger logger = Logger.getLogger(ReferenceController.class);

    @Autowired
    private SubjectService subjectService;

    @PostMapping(value = "/update_subject/")
    public ResponseEntity<SubjectsResult> updateSubject(@RequestBody SubjectRequest request) {

        SubjectsResult result = new SubjectsResult();

        try {
           result.setSubjects(subjectService.updateSubject(request.getSubjectName()));
        } catch (Exception e) {
            logger.error("Error getting references",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
