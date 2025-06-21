/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.request.LettersRequest;
import nl.christine.schwartze.server.controller.request.LocationLettersRequest;
import nl.christine.schwartze.server.controller.request.PersonLettersRequest;
import nl.christine.schwartze.server.controller.result.ApiResult;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.TextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Letters", description = "The letters API")
public class LetterController {

    Logger logger = LoggerFactory.getLogger(LetterController.class);

    @Autowired
    private LetterService letterService;

    @Autowired
    private TextService textService;

    @PostMapping(value = "/getLetter/")
    public ResponseEntity<LetterResult> getLetter(@RequestBody LetterRequest request) throws IOException {

        LetterResult result = new LetterResult();

        try {

            Letter letter = letterService.getLetterByNumber(request.getLetterNumber());
            if (letter != null) {
                result.setLetter(letter);
                if (canShowLetter(letter)) {
                    result.setLetterText(textService.getLetterText(letter.getNumber(), request.getLanguage()).getText());
                }
                result.setResult(ApiResult.OK);
            } else {
                result.setResult(ApiResult.NOT_OK);
            }
        } catch (Exception e) {
            logger.error("Error getting Letters", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getNextLetter/")
    public ResponseEntity<LetterResult> getNextLetter(@RequestBody LetterRequest request) throws IOException {
        LetterResult result = new LetterResult();

        try {
            Letter letter = letterService.getNextLetter(request.getLetterNumber());
            if (letter != null) {
                result.setLetter(letter);
                if (canShowLetter(letter)) {
                    result.setLetterText(textService.getLetterText(letter.getNumber(), request.getLanguage()).getText());
                }
                result.setResult(ApiResult.OK);
            } else {
                result.setResult(ApiResult.NOT_OK);
            }
        } catch (Exception e) {
            logger.error("Error getting Letters", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getPreviousLetter/")
    public ResponseEntity<LetterResult> getPreviousLetter(@RequestBody LetterRequest request) throws IOException {
        LetterResult result = new LetterResult();

        try {

            Letter letter = letterService.getPreviousLetter(request.getLetterNumber());
            if (letter != null) {
                result.setLetter(letter);
                if (canShowLetter(letter)) {
                    result.setLetterText(textService.getLetterText(letter.getNumber(), request.getLanguage()).getText());
                }
                result.setResult(ApiResult.OK);
            } else {
                result.setResult(ApiResult.NOT_OK);
            }
        } catch (Exception e) {
            logger.error("Error getting Letters", e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    private boolean canShowLetter(Letter letter) {
        return (letter.getCollectie() == null)
                || !letter.getCollectie().isDontShowLetter()
                || !(letter.getSenders().stream().filter(l -> !l.getHideLetters()).collect(Collectors.toList()).isEmpty());
    }


    @PostMapping(value = "/getLetters/")
    public ResponseEntity<LettersResult> getLetters(@RequestBody LettersRequest request) {

        LettersResult result = new LettersResult();

        List<Letter> letters = letterService.getLetters(request.getOrderBy());

        result.setLetters(letters);
        result.setResult(ApiResult.OK);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getLettersForLocation/")
    public ResponseEntity<LettersResult> getLettersForLocation(@RequestBody LocationLettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = letterService.getLettersForLocation(request.getLocationId());
            result.setLetters(letters);
            result.setResult(ApiResult.OK);

        } catch (Exception e) {
            logger.error("get_letters exception ", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/getLettersForPerson/")
    public ResponseEntity<LettersResult> getLettersForPerson(@RequestBody PersonLettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = letterService.getLettersForPerson(request.getId(), request.getOrderBy());
            result.setLetters(letters);
            result.setResult(ApiResult.OK);

        } catch (Exception e) {
            logger.error("get_letters exception ", e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
