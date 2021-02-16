/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.result.PageResult;
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
import java.util.*;
import java.util.stream.Collectors;

@Component("textFileService")
public class TextFileServiceImpl implements TextFileService {

    final String defaultLanguage = "nl";
    Logger logger = Logger.getLogger(TextFileServiceImpl.class);

    private final Comparator<File> reverseComparator;
    private final Comparator<File> comparator;

    private String lettersDirectory;

    @Autowired
    private SchwartzeProperties properties;

    @Autowired
    private TextProcessor processor;

    public TextFileServiceImpl() {
        reverseComparator = Comparator
                .comparing(File::getName, Comparator.nullsFirst(Comparator.reverseOrder()));
        comparator = Comparator.comparing(File::getName);
    }

    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
    }

    @Override
    public String getText(String type, String documentName, String language) {
        String fileName = lettersDirectory + "/" + type + "/" + language + "/" + documentName + ".txt";
        String result = getText(fileName);
        if (result == null) {
            fileName = lettersDirectory + "/" + type + "/" + defaultLanguage + "/" + documentName + ".txt";
            result = getText(fileName);
        }
        if (result == null) {
            result = "text file not found";
        }
        return result;
    }

    @Override
    public String getPage(String chapterId, String pageId, String language) {
        String fileName = lettersDirectory + "/pages/" + language + "/" + chapterId + "/" + pageId + ".txt";
        String result = getText(fileName);
        if (result == null) {
            fileName = lettersDirectory + "/pages/" + defaultLanguage + "/" + chapterId + "/" + pageId + ".txt";
            result = getText(fileName);
        }
        if (result == null) {
            result = "text file not found";
        }
        return result;
    }

    @Override
    public PageResult getNextPage(String chapterId, String pageId, String language) {
        return getPreviousNextPage(comparator, chapterId, pageId, language);
    }

    @Override
    public PageResult getPreviousPage(String chapterId, String pageId, String language) {
        return getPreviousNextPage(reverseComparator, chapterId, pageId, language);
    }

    private PageResult getPreviousNextPage(Comparator<File> pComparator, String chapterId, String pageId, String language) {
        PageResult pageResult;
        File dir = new File(lettersDirectory + "/pages/" + language + "/" + chapterId);
        File[] dirList = dir.listFiles();
        String nextPageId = null;
        Iterator<File> iterator = Arrays.stream(dirList)
                .sorted(pComparator)
                .collect(Collectors.toList()).iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(pageId + ".txt")) {
                if (iterator.hasNext()) {
                    nextPageId = iterator.next().getName().replace(".txt", "");
                }
                break;
            }
        }
        if (nextPageId != null) {
            pageResult = new PageResult();
            pageResult.setPageId(nextPageId);
            pageResult.setChapterId(chapterId);
            pageResult.setText(getPage(chapterId, nextPageId, language));
            return pageResult;
        }
        return getPreviousNextChapter(pComparator, chapterId, pageId, language);
    }

    @Override
    public PageResult getNextChapter(String chapterId, String pageId, String language) {
        return getPreviousNextChapter(comparator, chapterId, pageId, language);
    }

    @Override
    public PageResult getPreviousChapter(String chapterId, String pageId, String language) {
        return getPreviousNextChapter(reverseComparator, chapterId, pageId, language);
    }

    private PageResult getPreviousNextChapter(Comparator<File> pComparator, String chapterId, String pageId, String language) {
        PageResult pageResult;
        File dir = new File(lettersDirectory + "/pages/" + language + "/" + chapterId);
        File[] chapterDirFiles = new File(dir.getParent()).listFiles();
        Iterator<File> chapterIterator = Arrays.stream(chapterDirFiles)
                .sorted(pComparator)
                .collect(Collectors.toList()).iterator();
        String nextChapterId;
        String firstPageId;
        while (chapterIterator.hasNext()) {
            if (chapterIterator.next().getName().equals(chapterId)) {
                if (chapterIterator.hasNext()) {
                    String nextChapterDirName = chapterIterator.next().getAbsolutePath();
                    File f = new File(nextChapterDirName);
                    nextChapterId = f.getName();
                    File[] files = f.listFiles();
                    firstPageId = Arrays.stream(files)
                            .sorted(comparator)
                            .collect(Collectors.toList()).get(0).getName().replace(".txt", "");
                    pageResult = new PageResult();
                    pageResult.setChapterId(nextChapterId);
                    pageResult.setPageId(firstPageId);
                    pageResult.setText(getPage(nextChapterId, firstPageId, language));
                    return pageResult;
                }
            }
        }
        return null;
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
            result = null;
        }

        return result;
    }
}
