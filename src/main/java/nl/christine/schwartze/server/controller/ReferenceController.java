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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ReferenceController {
    Logger logger = LoggerFactory.getLogger(ReferenceController.class);

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
