/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.LetterResult;
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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class LetterUpdateController {

    Logger logger = LoggerFactory.getLogger(LetterUpdateController.class);

    @Autowired
    private LetterService letterService;

    @PostMapping(value = "/update_letter/")
    public ResponseEntity<LetterResult> updateLetter(@RequestBody LetterRequest request) {

        LetterResult result = new LetterResult();
        result.setResult(ApiResult.NOT_OK);

        try {
            Letter letter = letterService.updateLetter(request.getLetter());
            if (letter != null) {
                result.setLetter(letter);
                result.setResultCode(ApiResult.OK);
            }
        } catch (Exception e) {
            logger.error("Error updating letter comment", e);
            result.setMessage(e.getMessage() != null ? e.getMessage() : e.getClass().getName());
            result.setLetter(request.getLetter());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
