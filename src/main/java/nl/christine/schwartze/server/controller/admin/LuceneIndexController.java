/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.controller.result.IndexResult;
import nl.christine.schwartze.server.search.IndexFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LuceneIndexController {

    @Autowired
    private IndexFiles indexFiles;

    @PostMapping(value = "/index_files/")
    public ResponseEntity<IndexResult> createIndex() {

        IndexResult result = new IndexResult();

        result.setNumberIndexed(indexFiles.indexFiles());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
