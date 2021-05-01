/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.PageDao;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.model.PageReference;
import nl.christine.schwartze.server.service.PageService;
import nl.christine.schwartze.server.service.TextFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("pageService")
public class PageServiceImpl implements PageService {

    @Value("${defaultlanguage}")
    private String defaultLanguage;

    @Autowired
    private PageDao pageDao;

    @Autowired
    private TextFileService textFileService;

    @Override
    @Transactional("transactionManager")
    public Page getPage(String language, String pageNumber, String chapterNumber) {
        Page result = pageDao.getPage(pageNumber, chapterNumber);
        if(result == null){
            result = new Page();
            result.setChapterNumber(chapterNumber);
            result.setPageNumber(pageNumber);
        }
        result.setChapterTitle(textFileService.getChapterTitle(language, chapterNumber));
        return result;
    }

    @Override
    @Transactional("transactionManager")
    public Page addPage(String pageNumber, String chapterNumber) {
        return pageDao.addPage(pageNumber, chapterNumber);
    }

    @Override
    @Transactional("transactionManager")
    public Page updatePage(Page page) {
        return pageDao.updatePage(page);
    }

    @Override
    @Transactional("transactionManager")
    public void removePage(String pageNumber, String chapterNumber) {
        pageDao.removePage(pageNumber,chapterNumber);
    }

    @Override
    @Transactional("transactionManager")
    public Page addPageReference(String pageNumber, String chapterNumber, PageReference reference) {
        return pageDao.addReference(pageNumber,chapterNumber, reference);
    }

    @Override
    @Transactional("transactionManager")
    public Page removePageReference(String pageNumber, String chapterNumber, PageReference reference) {
        return pageDao.removeReference(pageNumber,chapterNumber, reference);
    }
}
