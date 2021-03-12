/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.model.ContentItem;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.ContentService;
import nl.christine.schwartze.server.text.TextReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("contentService")
public class ContentServiceImpl implements ContentService {

    Logger logger = Logger.getLogger(ContentServiceImpl.class);

    private final Comparator<File> comparator;
    private String lettersDirectory;

    @Autowired
    private TextReader textReader;

    @Autowired
    private SchwartzeProperties properties;

    public ContentServiceImpl() {
        comparator = Comparator.comparing(File::getName);
    }

    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
    }

    @Override
    public List<ContentItem> getContent(String language) {

        File chapterDir = new File(lettersDirectory + "/pages/" + language);
        return Arrays
                .stream(chapterDir.listFiles())
                .sorted(comparator)
                .map(dir -> {
                    ContentItem content = new ContentItem();
                    content.setChapterNumber(dir.getName());
                    content.setPageNumber(getPageNumber(dir));
                    content.setChapterTitle(textReader.getText(chapterDir + "/" + dir.getName() + "/title.ch"));
                    return content;
                })
                .collect(Collectors.toList());
    }

    private String getPageNumber(File dir) {
        return Arrays
                .stream(dir.listFiles())
                .sorted(comparator)
                .map(file -> file.getName().substring(0,file.getName().indexOf(".")))
                .findFirst()
                .orElse("");
    }
}
