/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.text.impl;

import nl.christine.schwartze.server.text.TextReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Component("textReader")
public class TextReaderImpl implements TextReader {

    Logger logger = Logger.getLogger(TextReaderImpl.class);

    @Override
    public String getText(String fileName) {

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
            result = null;
        }

        return result;
    }
}
