/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.EditReferenceLinkRequest;
import nl.christine.schwartze.server.controller.request.RemoveReferenceLinkRequest;
import nl.christine.schwartze.server.controller.request.UpdateReferencesRequest;
import nl.christine.schwartze.server.controller.result.UpdateReferencesResult;
import nl.christine.schwartze.server.model.References;
import nl.christine.schwartze.server.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin References", description = "")
public class AdminReferencesController {

    @Autowired
    private ReferenceService referenceService;

    @PostMapping(value = "/updateReferences/")
    public ResponseEntity<UpdateReferencesResult> updateReferences(@RequestBody UpdateReferencesRequest request) {
        UpdateReferencesResult result = new UpdateReferencesResult();

        References references = referenceService.updateReferences(request.getReferences());
        result.setReferences(references);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/removeReferenceLink/")
    public ResponseEntity<UpdateReferencesResult> removeReferenceLink(@RequestBody RemoveReferenceLinkRequest request) {
        UpdateReferencesResult result = new UpdateReferencesResult();

        referenceService.removeReferenceLink(request);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/editReferenceLink/")
    public ResponseEntity<UpdateReferencesResult> editReferenceLink(@RequestBody EditReferenceLinkRequest request) {
        UpdateReferencesResult result = new UpdateReferencesResult();

        References references = referenceService.editLink(request);
        result.setReferences(references);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
