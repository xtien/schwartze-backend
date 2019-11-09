/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import nl.christine.schwartze.server.service.LetterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class ImportDBController {

    @Autowired
    private LetterService letterService;

    private Logger logger = Logger.getLogger(ImportDBController.class);

    public LettersResult importDB() {

        LettersResult result = new LettersResult();

        List<ImportLetter> importLetters;

        try {

            importLetters = letterService.getImportLetters();

        } catch (Exception e) {
            logger.error("Error importing letters ", e);
            result.setResult(-1);
            return result;
        }



        for (ImportLetter importLetter : importLetters) {

            try {

                letterService.persistIfNotPresent(importLetter);

            } catch (Exception e) {
                logger.error("error persisting importede letters", e);
                result.setResult(-1);
                return result;
            }
        }

        return result;
    }
}
