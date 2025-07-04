/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.HomeTextRequest;
import nl.christine.schwartze.server.controller.request.PageTextRequest;
import nl.christine.schwartze.server.controller.result.HomeTextResult;
import nl.christine.schwartze.server.controller.result.PageTextResult;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.TextFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Home Text", description = "")
public class HomeTextController {

    @Autowired
    private TextFileService textFileService;

    @Autowired
    private PageService pageService;

    @PostMapping(value = "/getHomeText/")
    public ResponseEntity<HomeTextResult> getHomeText(@RequestBody HomeTextRequest request) {

        HomeTextResult result = new HomeTextResult();
        HttpStatus status = HttpStatus.OK;
        result.setText(textFileService.getText(request.getType(), request.getTextId(), request.getLanguage()));
        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "/switchLanguage/")
    public ResponseEntity<PageTextResult> switchLanguage(@RequestBody PageTextRequest request) {

        PageTextResult result = new PageTextResult();
        String switchedLanguage = ((request.getLanguage().equals("nl")) ? "en" : "nl");
        HttpStatus status = HttpStatus.OK;
        result.setText(textFileService.getPage(request.getChapterId(), request.getPageId(), switchedLanguage));
        result.setLanguage(switchedLanguage);
        return new ResponseEntity<>(result, status);
    }


}
