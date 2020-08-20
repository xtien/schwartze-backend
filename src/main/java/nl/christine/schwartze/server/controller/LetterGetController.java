/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Controller
@CrossOrigin(origins = {"https://pengo.christine.nl",
        "https://www.schwartze-ansingh.com",
        "https://www.schwartze-ansingh.nl",
        "https://schwartze-ansingh.com",
        "https://schwartze-ansingh.nl"}, maxAge = 7200)
public class LetterGetController {

    Logger logger = Logger.getLogger(LetterGetController.class);

    private String lettersDirectory;
    private String textDocumentName;

    @Autowired
    private SchwartzeProperties properties;

    @Autowired
    private LetterService letterService;


    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
        textDocumentName = properties.getProperty("text_document_name");
    }

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

        if(new File(fileName).exists()) {

            try (BufferedReader rd = new BufferedReader(new InputStreamReader(java.nio.file.Files.newInputStream(Paths.get(fileName))))) {
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result += "<br>" + line;
                }
                int i = 1;
                result = result.replaceAll("    ", "&nbsp&nbsp&nbsp&nbsp;");
                result = result.replaceAll("/", "<BR><BR>-----<BR><BR>");
            } catch (Exception e) {
                logger.error("Error getting letter text", e);
                result = "text file not found";
            }
        } else {
            result = "text file not found";
        }
        return result;
    }
}
