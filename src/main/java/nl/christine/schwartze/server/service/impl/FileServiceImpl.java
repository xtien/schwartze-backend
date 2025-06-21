/*
 * Copyright (c) 2025, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.service.FileService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;

@Component("fileService")
public class FileServiceImpl implements FileService {

    @Override
    public String readFile(String fileName) {
        String result = "";

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(java.nio.file.Files.newInputStream(Paths.get(fileName))))) {
            String line = "";
            while ((line = rd.readLine()) != null) {
                result += "<BR>" + line;
            }
            int i = 1;
            result = result.replace("    ", "&nbsp&nbsp&nbsp&nbsp;");
            result = result.replace("<BR>/<BR>", "<BR><BR>-----<BR><BR>");
        } catch (Exception e) {
            result = "text file not found";
        }
        return result;
    }

    @Override
    public boolean existsFile(String fileName) {
        return new File(fileName).exists();
    }
}
