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
    public Page getPage(String pageNumber) {
        return pageDao.getPage(pageNumber);
    }

    @Override
    @Transactional("transactionManager")
    public void addPage(String pageNumber) {
        pageDao.addPage(pageNumber);
    }

    @Override
    @Transactional("transactionManager")
    public void removePage(String pageNumber) {
        pageDao.removePage(pageNumber);
    }

    @Override
    @Transactional("transactionManager")
    public void addPageReference(String pageNumber, PageReference reference) {
        pageDao.addReference(pageNumber, reference);
    }

    @Override
    @Transactional("transactionManager")
    public void removePageReference(String pageNumber, PageReference reference) {
        pageDao.removeReference(pageNumber, reference);
    }
}
