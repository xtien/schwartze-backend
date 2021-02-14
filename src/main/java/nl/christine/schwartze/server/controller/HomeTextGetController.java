/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.HomeTextRequest;
import nl.christine.schwartze.server.controller.request.PageTextRequest;
import nl.christine.schwartze.server.controller.result.HomeTextResult;
import nl.christine.schwartze.server.controller.result.PageTextResult;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.TextFileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class HomeTextGetController {

     @Autowired
    private TextFileService textFileService;

    @Autowired
    private PageService pageService;

    @PostMapping(value = "/get_page_text/")
    public ResponseEntity<HomeTextResult> getText(@RequestBody HomeTextRequest request) {

        HomeTextResult result = new HomeTextResult();
        HttpStatus status = HttpStatus.OK;
        result.setText(textFileService.getText(request.getType(), request.getTextId(), request.getLanguage()));
        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "/get_page_page/")
    public ResponseEntity<PageTextResult> getPage(@RequestBody PageTextRequest request) {

        PageTextResult result = new PageTextResult();
        HttpStatus status = HttpStatus.OK;
        result.setText(textFileService.getPage(request.getChapterId(), request.getPageId(), request.getLanguage()));
        result.setPage(pageService.getPage(request.getPageId(), request.getChapterId()));
        return new ResponseEntity<>(result, status);
    }
}
