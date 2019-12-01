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
import nl.christine.schwartze.server.service.TextService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UpdateTextController {

    Logger logger = Logger.getLogger(UpdateTextController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private TextService textService;

    @CrossOrigin(origins = Application.UI_HOST)
    @GetMapping(value = "/update_text/")
    public ResponseEntity<TextResult> updateText(@RequestBody TextRequest request) {

        TextResult result = new TextResult();
        HttpStatus status = HttpStatus.OK;

        try {
             Text text = textService.updateText(request.getText());
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
