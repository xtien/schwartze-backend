/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.EditReferenceLinkRequest;
import nl.christine.schwartze.server.controller.request.RemoveReferenceLinkRequest;
import nl.christine.schwartze.server.controller.request.UpdateReferencesRequest;
import nl.christine.schwartze.server.controller.result.UpdateReferencesResult;
import nl.christine.schwartze.server.model.References;
import nl.christine.schwartze.server.service.ReferenceService;
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
@CrossOrigin(origins = Application.UI_HOST)
public class ReferencesUpdateController {

    @Autowired
    private ReferenceService referenceService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/update_references/")
    public ResponseEntity<UpdateReferencesResult> updateReferences(@RequestBody UpdateReferencesRequest request) {
        UpdateReferencesResult result = new UpdateReferencesResult();

        References references = referenceService.updateReferences(request.getReferences());
        result.setReferences(references);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/remove_reference_link/")
    public ResponseEntity<UpdateReferencesResult> removeReferenceLink(@RequestBody RemoveReferenceLinkRequest request) {
        UpdateReferencesResult result = new UpdateReferencesResult();

        References references = referenceService.removeReferenceLink(request);
        result.setReferences(references);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/edit_reference_link/")
    public ResponseEntity<UpdateReferencesResult> editLink(@RequestBody EditReferenceLinkRequest request) {
        UpdateReferencesResult result = new UpdateReferencesResult();

        References references = referenceService.editLink(request);
        result.setReferences(references);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
