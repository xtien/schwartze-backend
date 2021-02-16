/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.PageDao;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.model.PageReference;
import nl.christine.schwartze.server.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("pageService")
public class PageServiceImpl implements PageService {

    @Autowired
    private PageDao pageDao;

    @Override
    @Transactional("transactionManager")
    public Page getPage(String pageNumber, String chapterNumber) {
        Page result = pageDao.getPage(pageNumber, chapterNumber);
        if(result == null){
            result = new Page();
            result.setChapterNumber(chapterNumber);
            result.setPageNumber(pageNumber);
        }
        return result;
    }

    @Override
    @Transactional("transactionManager")
    public void addPage(String pageNumber, String chapterNumber) {
        pageDao.addPage(pageNumber, chapterNumber);
    }

    @Override
    @Transactional("transactionManager")
    public void removePage(String pageNumber, String chapterNumber) {
        pageDao.removePage(pageNumber,chapterNumber);
    }

    @Override
    @Transactional("transactionManager")
    public void addPageReference(String pageNumber, String chapterNumber, PageReference reference) {
        pageDao.addReference(pageNumber,chapterNumber, reference);
    }

    @Override
    @Transactional("transactionManager")
    public Page removePageReference(String pageNumber, String chapterNumber, PageReference reference) {
        return pageDao.removeReference(pageNumber,chapterNumber, reference);
    }
}
