/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.TextFileService;
import nl.christine.schwartze.server.service.TextProcessor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Component("textFileService")
public class TextFileServiceImpl implements TextFileService {

    Logger logger = Logger.getLogger(TextFileServiceImpl.class);

    private String lettersDirectory;

    @Autowired
    private SchwartzeProperties properties;

    @Autowired
    private TextProcessor processor;

    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
    }

    @Override
    public String getText(String type, String documentName, String language) {
        String fileName = lettersDirectory + "/" + type + "/"  + language + "/" + documentName + ".txt";
        return processor.process(getText(fileName));
    }

    @Override
    public String getPage(String chapterId, String pageId, String language) {
        String fileName = lettersDirectory + "/pages/" + language + "/" + chapterId + "/" + pageId + ".txt";
        return getText(fileName);
    }

    private String getText(String fileName) {

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
