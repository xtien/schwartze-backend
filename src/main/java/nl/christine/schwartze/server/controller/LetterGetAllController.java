/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.LettersRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LetterGetAllController {

    Logger logger = LoggerFactory.getLogger(LetterGetAllController.class);

    @Autowired
    private LetterService letterService;

    @PostMapping(value = "/get_letters/")
    public ResponseEntity<LettersResult> getLetters(@RequestBody LettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = letterService.getLetters();
            result.setLetters(letters);
            result.setResult(ApiResult.OK);

        } catch (Exception e) {
            logger.error("get_letters exception ", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/get_letters_by_date/")
    public ResponseEntity<LettersResult> getLettersByDate(@RequestBody LettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = letterService.getLettersByDate();
            result.setLetters(letters);
            result.setResult(ApiResult.OK);

        } catch (Exception e) {
            logger.error("get_letters exception ", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
