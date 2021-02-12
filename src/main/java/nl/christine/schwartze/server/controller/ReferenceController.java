/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.GetReferencesRequest;
import nl.christine.schwartze.server.controller.result.ReferencesResult;
import nl.christine.schwartze.server.model.References;
import nl.christine.schwartze.server.service.ReferenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class ReferenceController {
    Logger logger = Logger.getLogger(ReferenceController.class);

    @Autowired
    private ReferenceService referenceService;

    @PostMapping(value = "/get_references/")
    public ResponseEntity<ReferencesResult> getPerson(@RequestBody GetReferencesRequest request) {

        ReferencesResult result = new ReferencesResult();

        try {

            References references = referenceService.getReferences("site");
            if (references != null) {
                result.setReferences(references);
            }
        } catch (Exception e) {
            logger.error("Error getting references", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
