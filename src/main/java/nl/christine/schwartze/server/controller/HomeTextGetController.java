/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.HomeTextRequest;
import nl.christine.schwartze.server.controller.result.HomeTextResult;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        "https://schwartze-ansingh.nl"}, maxAge = 14400)
public class HomeTextGetController {

    private String lettersDirectory;

    Logger logger = Logger.getLogger(HomeTextGetController.class);

    @Autowired
    private SchwartzeProperties properties;

    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
     }

    @PostMapping(value = "/get_page_text/")
    public ResponseEntity<HomeTextResult> getText(@RequestBody HomeTextRequest request) {

        HomeTextResult result = new HomeTextResult();
        HttpStatus status = HttpStatus.OK;

        try {
            result.setText(getText(request.getTextId()));
        } catch (IOException e) {
            logger.error("get_text exception ", e);
            result.setErrorText(e.getClass().getCanonicalName());
        }

        return new ResponseEntity<>(result, status);
    }

    private String getText(String documentName) throws IOException {

        String fileName = lettersDirectory + "/text/"+ documentName + ".txt";
        String result = "";

        if (new File(fileName).exists()) {

            try (BufferedReader rd = new BufferedReader(new InputStreamReader(java.nio.file.Files.newInputStream(Paths.get(fileName))))) {
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result += line;
                }
              } catch (Exception e) {
                logger.error("Error getting home text", e);
                result = "text file not found";
            }
        } else {
            result = "text file not found";
        }
        return result;
    }

}
