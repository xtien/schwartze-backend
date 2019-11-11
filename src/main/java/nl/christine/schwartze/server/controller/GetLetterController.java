/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.LetterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class GetLetterController {

    Logger logger = Logger.getLogger(GetLetterController.class);

    private final String lettersDirectory;
    private final String textDocumentName;

    @Autowired
    private LetterService letterService;

    public GetLetterController() {

        SchwartzeProperties.init();
        lettersDirectory = SchwartzeProperties.getProperty("letters_directory");
        textDocumentName = SchwartzeProperties.getProperty("text_document_name");
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_letter_details/")
    public ResponseEntity<LetterResult> getLetter(@RequestBody LetterRequest request) throws IOException {

        LetterResult result = new LetterResult();

        try {

            Letter letter = letterService.getLetterByNumber(request.getLetterNumber());
            result.setLetter(letter);
            result.setResult(LettersResult.OK);

        } catch (Exception e) {
            logger.error("Error getting Letters", e);
        }

        result.setLetterText(getLetterText(request.getLetterNumber()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String getLetterText(int letterNumber) throws IOException {

        String fileName = lettersDirectory + "/" + letterNumber + "/" + textDocumentName;
        String result = "";

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(java.nio.file.Files.newInputStream(Paths.get(fileName))))) {
            String line = "";
            while ((line = rd.readLine()) != null) {
                result += "<br>" + line;
            }
            int i = 1;
            result = result.replaceAll("    ", "&nbsp&nbsp&nbsp&nbsp;");
            result = result.replaceAll("/", "<BR><BR><i>blad " + ++i + "</i><BR><BR>");
        } catch (Exception e) {
            logger.error("Error getting letter text", e);

        }

        return result;
    }
}
