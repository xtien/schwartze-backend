/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.PersonLettersRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class LettersFromPersonController {

    Logger logger = Logger.getLogger(LettersFromPersonController.class);

    @Autowired
    private LetterService letterService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_letters_from_person/")
    public ResponseEntity<LettersResult> getLetters(@RequestBody PersonLettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = letterService.getLettersFromPerson(request.getFromId());
            result.setLetters(letters);
            result.setResult(LettersResult.OK);

        } catch (Exception e) {
            logger.error("get_letters exception ", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
