/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.PageRequest;
import nl.christine.schwartze.server.controller.result.PageResult;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.service.PageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class GetPageReferencesController {

    Logger logger = Logger.getLogger(GetPageReferencesController.class);

    @Autowired
    private PageService pageService;

    @PostMapping(value = "/get_page_references/")
    public ResponseEntity<PageResult> getPage(@RequestBody PageRequest request) throws IOException {

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
