/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.request.PageReferenceRequest;
import nl.christine.schwartze.server.controller.request.PageRequest;
import nl.christine.schwartze.server.controller.result.PageResult;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.TextFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class UpdatePageController {

    @Autowired
    private PageService pageService;

    @Autowired
    private TextFileService textFileService;

    @PostMapping(value = "/add_page/")
    public ResponseEntity<PageResult> addPage(@RequestBody PageRequest request) {
        PageResult result = new PageResult();
        pageService.addPage(request.getPageNumber(), request.getChapterNumber());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/update_page/")
    public ResponseEntity<PageResult> updatePage(@RequestBody PageRequest request) {
        PageResult result = new PageResult();
        pageService.addPage(request.getPageNumber(), request.getChapterNumber());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/remove_page/")
    public ResponseEntity<PageResult> removePage(@RequestBody PageRequest request) {
        PageResult result = new PageResult();
        pageService.removePage(request.getPageNumber(), request.getChapterNumber());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/add_page_reference/")
    public ResponseEntity<PageResult> addPageReference(@RequestBody PageReferenceRequest request) {
        PageResult result = new PageResult();
        pageService.addPageReference(request.getPageNumber(), request.getChapterNumber(), request.getReference());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/remove_page_reference/")
    public ResponseEntity<PageResult> removePageReference(@RequestBody PageReferenceRequest request) {
        PageResult result = new PageResult();
        Page page = pageService.removePageReference(request.getPageNumber(),request.getChapterNumber(), request.getReference());
        result.setText(textFileService.getPage(request.getChapterNumber(), request.getPageNumber(), "nl"));
        result.setPage(page);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
