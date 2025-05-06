/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.AddLetterRequest;
import nl.christine.schwartze.server.controller.request.DeleteLetterRequest;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.AddLetterResult;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.DeleteLetterResult;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.exception.LetterNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Letter", description = "")
public class AdminLetterController {

    Logger logger = LoggerFactory.getLogger(AdminLetterController.class);

    @Autowired
    private LetterService letterService;

    @PostMapping(value = "/addLetter/")
    public ResponseEntity<AddLetterResult> addLetter(@RequestBody AddLetterRequest request) {
        AddLetterResult result = new AddLetterResult();

        if (request.getLetter().getId() > 0) {
            result.setText("letter exists");
        } else {
            Letter resultLetter = letterService.addLetter(request.getLetter());
            result.setLetter(resultLetter);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/deleteLetter/")
    public ResponseEntity<DeleteLetterResult> addLetter(@RequestBody DeleteLetterRequest request) {
        DeleteLetterResult result = new DeleteLetterResult();
        HttpStatus status = HttpStatus.OK;

        try {
            letterService.deleteLetter(request.getLetter());
        } catch (LetterNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "/updateLetterComment/")
    public ResponseEntity<LetterResult> updateLetterComment(@RequestBody LetterRequest request) {

        LetterResult result = new LetterResult();
        result.setResult(ApiResult.NOT_OK);

        try {
            Letter letter = letterService.updateLetterComment(request.getLetterNumber(), request.getComment(), request.getDate());
            if (letter != null) {
                result.setLetter(letter);
                result.setResultCode(ApiResult.OK);
            }
        } catch (Exception e) {
            logger.error("Error updating letter comment",e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/updateLetter/")
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
