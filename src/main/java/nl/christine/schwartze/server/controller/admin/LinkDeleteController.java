/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.service.LinkService;
import nl.christine.schwartze.server.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class LinkDeleteController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/delete_link/")
    public ResponseEntity<EditLinkResult> deleteLink(@RequestBody EditLinkRequest request) {

        EditLinkResult result = new EditLinkResult();

        if (request.getLocationId() != null) {
            result.setLocation(linkService.deleteLocationLink(request));
        } else if (request.getPersonId() != null) {
            result.setPerson(linkService.deletePersonLink(request));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
