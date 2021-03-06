/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.controller.result.TextResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.dao.SubjectDao;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.TextService;
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
public class TextUpdateController {

    Logger logger = Logger.getLogger(TextUpdateController.class);

    @Autowired
    private TextService textService;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private LetterDao letterDao;

    @PostMapping(value = "/update_text/")
    public ResponseEntity<TextResult> updateText(@RequestBody TextRequest request) {

        TextResult result = new TextResult();
        HttpStatus status = HttpStatus.OK;

        try {
            Text text = textService.updateText(request);

            if (request.getPersonId() != null) {
                result.setPerson(personDao.getPerson(request.getPersonId()));
            }
            if (request.getLocationId() != null) {
                result.setLocation(locationDao.getLocation(request.getLocationId()));
            }
            if (request.getLetterId() != null) {
                result.setLetter(letterDao.getLetterForId(request.getLetterId()));
            }
            if(request.getSubjectId() !=null){
                result.setSubject(subjectDao.getSubjectById(request.getSubjectId()));
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
