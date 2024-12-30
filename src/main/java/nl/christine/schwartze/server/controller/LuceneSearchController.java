/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.SearchRequest;
import nl.christine.schwartze.server.controller.result.SearchResult;
import nl.christine.schwartze.server.search.SearchFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LuceneSearchController {

    Logger logger = LoggerFactory.getLogger(LuceneSearchController.class);
    private final static String PATTERN = "^[\\p{L}0-9\\-\\s']+$";

    @Autowired
    private SearchFiles searchFiles;

    @PostMapping(value = "/search_letters/")
    public ResponseEntity<SearchResult> searchIndex(@RequestBody SearchRequest request) {

        SearchResult result = new SearchResult();
        if (request.getSearchTerm().matches(PATTERN)) {
            try {
                result.setLetters(searchFiles.search(request.getSearchTerm()));
                result.setNumberOfResults(result.getLetters().size());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
