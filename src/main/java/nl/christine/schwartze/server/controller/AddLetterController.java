/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.AddLetterRequest;
import nl.christine.schwartze.server.controller.result.AddLetterResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import nl.christine.schwartze.server.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class AddLetterController {

    @Autowired
    private LetterService letterService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/add_letter/")
    public ResponseEntity<AddLetterResult> addLetter(@RequestBody AddLetterRequest request) {
        AddLetterResult result = new AddLetterResult();

        if (request.getLetter().getNumber() > 0) {
            result.setErrorText("letter exists");
        } else {
            Letter resultLetter = letterService.addLetter(request.getLetter());
            result.setLetter(resultLetter);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
