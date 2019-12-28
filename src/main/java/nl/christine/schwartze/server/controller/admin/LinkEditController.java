/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = Application.UI_HOST, maxAge = 7200)
public class LinkEditController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/edit_link/")
    public ResponseEntity<EditLinkResult> editLink(@RequestBody EditLinkRequest request) {
        EditLinkResult result = new EditLinkResult();

        if (request.getLocationId() != null) {
            result = locationService.editLink(request);
        }

        if (request.getPersonId() != null) {
            result = personService.editLink(request);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
