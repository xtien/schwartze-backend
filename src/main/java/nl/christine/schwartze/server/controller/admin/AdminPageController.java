/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.PageReferenceRequest;
import nl.christine.schwartze.server.controller.request.PageRequest;
import nl.christine.schwartze.server.controller.request.UpdatePageRequest;
import nl.christine.schwartze.server.controller.result.PageResult;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.TextFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Page", description = "")
public class AdminPageController {

    @Autowired
    private PageService pageService;

    @Autowired
    private TextFileService textFileService;

    @PostMapping(value = "/addPage/")
    public ResponseEntity<PageResult> addPage(@RequestBody PageRequest request) {
        PageResult result = new PageResult();
        pageService.addPage(request.getPageNumber(), request.getChapterNumber());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/updatePage/")
    public ResponseEntity<PageResult> updatePage(@RequestBody UpdatePageRequest request) {
        PageResult result = new PageResult();
        Page page = pageService.updatePage(request.getPage());
        result.setPage(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/removePage/")
    public ResponseEntity<PageResult> removePage(@RequestBody PageRequest request) {
        PageResult result = new PageResult();
        pageService.removePage(request.getPageNumber(), request.getChapterNumber());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/addPageReference/")
    public ResponseEntity<PageResult> addPageReference(@RequestBody PageReferenceRequest request) {
        PageResult result = new PageResult();
        Page page = pageService.addPageReference(request.getPageNumber(), request.getChapterNumber(), request.getReference());
        result.setPage(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/removePageReference/")
    public ResponseEntity<PageResult> removePageReference(@RequestBody PageReferenceRequest request) {
        PageResult result = new PageResult();
        Page page = pageService.removePageReference(request.getPageNumber(), request.getChapterNumber(), request.getReference());
        result.setText(textFileService.getPage(request.getChapterNumber(), request.getPageNumber(), "nl"));
        result.setPage(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
