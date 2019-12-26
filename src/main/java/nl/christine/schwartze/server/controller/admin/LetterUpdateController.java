/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@CrossOrigin(origins = Application.UI_HOST)
public class LetterUpdateController {

    Logger logger = Logger.getLogger(LetterUpdateController.class);

    @Autowired
    private LetterService letterService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/update_letter_details/")
    @Transactional("transactionManager")
    public ResponseEntity<LetterResult> updateLetterComment(@RequestBody LetterRequest request) {

        LetterResult result = new LetterResult();
        result.setResult(LetterResult.NOT_OK);

        try {
            Letter letter = letterService.updateLetterComment(request.getLetterNumber(), request.getComment(), request.getDate());
            if (letter != null) {
                result.setLetter(letter);
                result.setResultCode(LetterResult.OK);
            }
        } catch (Exception e) {
            logger.error("Error updating letter comment",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
