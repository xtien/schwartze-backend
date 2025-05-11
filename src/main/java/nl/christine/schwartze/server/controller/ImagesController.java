/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.ImagesRequest;
import nl.christine.schwartze.server.controller.result.ImagesResult;
import nl.christine.schwartze.server.image.ImageService;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.LetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Images", description = "")
public class ImagesController {

    @Autowired
    private SchwartzeProperties properties;

    @Autowired
    private ImageService imageService;

    @Autowired
    private LetterService letterService;

    @PostMapping(value = "/getLetterImages/")
    @Transactional("transactionManager")
    public ResponseEntity<ImagesResult> getLetterImages(@RequestBody ImagesRequest request) {

        ImagesResult result = new ImagesResult();

        try {

            Letter letter = letterService.getLetterByNumber(request.getLetterNumber());
            if (letter.getCollectie() == null || !letter.getCollectie().isDontShowLetter()) {
                List<String> images = imageService.getImages(request.getLetterNumber());
                if (images.isEmpty()) {
                    return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
                }
                result.setImages(images);
            } else {
                return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
