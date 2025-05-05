/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.DeleteLetterRequest;
import nl.christine.schwartze.server.controller.result.DeleteLetterResult;
import nl.christine.schwartze.server.exception.LetterNotFoundException;
import nl.christine.schwartze.server.service.LetterService;
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
public class LetterDeleteController {

    @Autowired
    private LetterService letterService;

    @PostMapping(value = "/delete_letter/")
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

}
