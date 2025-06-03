/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.GetReferencesRequest;
import nl.christine.schwartze.server.controller.request.PageRequest;
import nl.christine.schwartze.server.controller.result.PageResult;
import nl.christine.schwartze.server.controller.result.ReferencesResult;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.model.References;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.ReferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "References", description = "")
public class ReferenceController {
    Logger logger = LoggerFactory.getLogger(ReferenceController.class);

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private PageService pageService;

    @PostMapping(value = "/getReferences/")
    public ResponseEntity<ReferencesResult> getReferences(@RequestBody GetReferencesRequest request) {

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

    @PostMapping(value = "/getPageReferences/")
    public ResponseEntity<PageResult> getPageReferences(@RequestBody PageRequest request) {

        PageResult result = new PageResult();

        try {

            Page page = pageService.getPage(request.getLanguage(), request.getPageNumber(), request.getChapterNumber());

            if (page != null) {
                result.setPage(page);
            }
        } catch (Exception e) {
            logger.error("Error getting Page References", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
