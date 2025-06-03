/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.PageTextRequest;
import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.controller.result.PageResult;
import nl.christine.schwartze.server.controller.result.PageTextResult;
import nl.christine.schwartze.server.controller.result.TextResult;
import nl.christine.schwartze.server.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Page", description = "")
public class PageController {

    @Autowired
    private TextFileService textFileService;

    @Autowired
    private PageService pageService;

    @PostMapping(value = "/getPage/")
    public ResponseEntity<PageTextResult> getPage(@RequestBody PageTextRequest request) {

        PageTextResult result = new PageTextResult();
        HttpStatus status = HttpStatus.OK;
        result.setText(textFileService.getPage(request.getChapterId(), request.getPageId(), request.getLanguage()));
        result.setPage(pageService.getPage(request.getLanguage(), request.getPageId(), request.getChapterId()));
        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "/getNextPage/")
    public ResponseEntity<PageTextResult> getNextPage(@RequestBody PageTextRequest request) {

        PageTextResult result = new PageTextResult();
        HttpStatus status = HttpStatus.OK;
        PageResult pageResult = textFileService.getNextPage(request.getChapterId(), request.getPageId(), request.getLanguage());
        if (pageResult != null) {
            result.setPage(pageService.getPage(request.getLanguage(), pageResult.getPageId(), pageResult.getChapterId()));
            result.setText(pageResult.getText());
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "/getPreviousPage/")
    public ResponseEntity<PageTextResult> getPreviousPage(@RequestBody PageTextRequest request) {

        PageTextResult result = new PageTextResult();
        HttpStatus status = HttpStatus.OK;
        PageResult pageResult = textFileService.getPreviousPage(request.getChapterId(), request.getPageId(), request.getLanguage());
        if (pageResult != null) {
            result.setPage(pageService.getPage(request.getLanguage(), pageResult.getPageId(), pageResult.getChapterId()));
            result.setText(pageResult.getText());
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "getNextChapter")
    public ResponseEntity<PageTextResult> getNextChapter(@RequestBody PageTextRequest request) {

        PageTextResult result = new PageTextResult();
        HttpStatus status = HttpStatus.OK;
        PageResult pageResult = textFileService.getNextChapter(request.getChapterId(), request.getPageId(), request.getLanguage());
        if (pageResult != null) {
            result.setPage(pageService.getPage(request.getLanguage(), pageResult.getPageId(), pageResult.getChapterId()));
            result.setText(pageResult.getText());
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping(value = "getPreviousChapter")
    public ResponseEntity<PageTextResult> getPreviousChapter(@RequestBody PageTextRequest request) {

        PageTextResult result = new PageTextResult();
        HttpStatus status = HttpStatus.OK;
        PageResult pageResult = textFileService.getPreviousChapter(request.getChapterId(), request.getPageId(), request.getLanguage());
        if (pageResult != null) {
            result.setPage(pageService.getPage(request.getLanguage(), pageResult.getPageId(), pageResult.getChapterId()));
            result.setText(pageResult.getText());
        }
        return new ResponseEntity<>(result, status);
    }

}
