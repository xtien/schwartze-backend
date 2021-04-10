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
import nl.christine.schwartze.server.text.TextReader;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

@Component("textFileService")
public class TextFileServiceImpl implements TextFileService {

    final String defaultLanguage = "nl";

    private final Comparator<File> comparator = Comparator.comparingInt(this::num);
    private final Comparator<File> reverseComparator = Comparator.comparingInt(this::num).reversed();

    private int num(File file) {
        String fileName = file.getName();
        if (fileName.contains(".")) {
            fileName = file.getName().substring(0, file.getName().indexOf("."));
        }
        return Integer.parseInt(NumberUtils.isCreatable(fileName) ? fileName : "0");
    }

    private String lettersDirectory;

    @Autowired
    private SchwartzeProperties properties;

    @Autowired
    private TextReader textReader;

    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
    }

    @Override
    public String getText(String type, String documentName, String language) {
        String fileName = lettersDirectory + "/" + type + "/" + language + "/" + documentName + ".txt";
        String result = textReader.getText(fileName);
        if (result == null) {
            fileName = lettersDirectory + "/" + type + "/" + defaultLanguage + "/" + documentName + ".txt";
            result = textReader.getText(fileName);
        }
        if (result == null) {
            result = "text file not found";
        }
        return result;
    }

    @Override
    public String getPage(String chapterId, String pageId, String language) {
        String fileName = lettersDirectory + "/pages/" + language + "/" + chapterId + "/" + pageId + ".txt";
        String result = textReader.getText(fileName);
        if (result == null) {
            fileName = lettersDirectory + "/pages/" + defaultLanguage + "/" + chapterId + "/" + pageId + ".txt";
            result = textReader.getText(fileName);
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
        File[] dirList = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".txt"));
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

    @Override
    public String getChapterTitle(String language, String chapterNumber) {
        return textReader.getText(lettersDirectory + "/pages/" + language + "/" + chapterNumber + "/title.ch");
    }

    private PageResult getPreviousNextChapter(Comparator<File> pComparator, String chapterId, String pageId, String language) {
        PageResult pageResult;
        File dir = new File(lettersDirectory + "/pages/" + language + "/" + chapterId);
        File[] chapterDirFiles = new File(dir.getParent()).listFiles((d, name) -> NumberUtils.isCreatable(name.toLowerCase()));
        Iterator<File> chapterIterator = Arrays.stream(chapterDirFiles)
                .sorted(pComparator)
                .collect(Collectors.toList()).iterator();
        String nextChapterId;
        String firstPageId;
        while (chapterIterator.hasNext()) {
            File next = chapterIterator.next();
            if (next.getName().equals(chapterId)) {
                if (chapterIterator.hasNext()) {
                    String nextChapterDirName = chapterIterator.next().getAbsolutePath();
                    File f = new File(nextChapterDirName);
                    nextChapterId = f.getName();
                    File[] files = f.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));
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
}
