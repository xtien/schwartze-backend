/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import nl.christine.schwartze.server.service.ImportLetterService;
import nl.christine.schwartze.server.service.LetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Profile("import")
@Tag(name = "Admin Import", description = "")
public class ImportDBController {

    @Autowired
    private LetterService letterService;

    @Autowired
    private ImportLetterService importLetterService;

    private Logger logger = LoggerFactory.getLogger(ImportDBController.class);

    public LettersResult importDB() {

        LettersResult result = new LettersResult();

        List<ImportLetter> importLetters;

        try {

            importLetters = importLetterService.getImportLetters();

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
