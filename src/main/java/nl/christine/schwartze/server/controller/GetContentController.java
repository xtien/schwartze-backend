/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.ContentRequest;
import nl.christine.schwartze.server.controller.result.ContentResult;
import nl.christine.schwartze.server.model.ContentItem;
import nl.christine.schwartze.server.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetContentController {

    @Autowired
    ContentService contentService;

    @PostMapping(value = "/get_content/")
    public ResponseEntity<ContentResult> addPage(@RequestBody ContentRequest request) {
        ContentResult result = new ContentResult();
        List<ContentItem> list = contentService.getContent(request.getLanguage());
        result.setContent(list);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
