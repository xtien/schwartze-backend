/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.result.IndexResult;
import nl.christine.schwartze.server.search.IndexFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Lucene", description = "")
public class AdminLuceneIndexController {

    @Autowired
    private IndexFiles indexFiles;

    @PostMapping(value = "/indexFiles/")
    public ResponseEntity<IndexResult> createIndex() {

        IndexResult result = new IndexResult();

        result.setNumberIndexed(indexFiles.indexFiles());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
