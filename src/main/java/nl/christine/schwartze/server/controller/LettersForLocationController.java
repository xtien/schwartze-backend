/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.LocationLettersRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class LettersForLocationController {

    Logger logger = LoggerFactory.getLogger(LettersForLocationController.class);

    @Autowired
    private LetterService letterService;

    @PostMapping(value = "/get_letters_for_location/")
    public ResponseEntity<LettersResult> getLetters(@RequestBody LocationLettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = letterService.getLettersForLocation(request.getLocationId());
            result.setLetters(letters);
            result.setResult(ApiResult.OK);

        } catch (Exception e) {
            logger.error("get_letters exception ", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
