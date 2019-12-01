/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.controller.result.TextResult;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class GetTextController {

    Logger logger = Logger.getLogger(GetTextController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_person_text/")
    public ResponseEntity<TextResult> getPersonText(@RequestBody TextRequest request) {

        TextResult result = new TextResult();
        HttpStatus status = HttpStatus.OK;

        try {
            Text text = personService.getText(request.getId());
            if (text != null) {
                result.setText(text);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            logger.error("get_text exception ", e);
        }

        return new ResponseEntity<>(result, status);
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_location_text/")
    public ResponseEntity<TextResult> getLocationText(@RequestBody TextRequest request) {

        TextResult result = new TextResult();
        HttpStatus status = HttpStatus.OK;

        try {
            Text text = locationService.getText(request.getId());
            if (text != null) {
                result.setText(text);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            logger.error("get_text exception ", e);
        }

        return new ResponseEntity<>(result, status);
    }
}
