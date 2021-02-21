/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.PageDao;
import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.model.PageReference;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Component("pageDao")
public class PageDaoImpl implements PageDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    @Override
    public Page addPage(String pageNumber, String chapterNumber) {
        Page page = getPage(pageNumber, chapterNumber);
        if (page == null) {
            page = new Page();
            page.setPageNumber(pageNumber);
            page.setChapterNumber(chapterNumber);
            entityManager.persist(page);
        }
        return page;
    }

    @Override
    public Page updatePage(Page page) {
        Page existingPage = getPage(page.getPageNumber(), page.getChapterNumber());
        if (existingPage == null) {
            entityManager.persist(page);
            return page;
        }

        existingPage.setPictureUrl(page.getPictureUrl());
        return existingPage;
    }

    @Override
    public void removePage(String pageNumber, String chapterNumber) {
        Page existingPage = getPage(pageNumber, chapterNumber);
        if (existingPage == null) {
            entityManager.remove(existingPage);
        }
    }

    @Override
    public Page addReference(String pageNumber, String chapterNumber, PageReference reference) {
        Page page = getPage(pageNumber, chapterNumber);
        if (reference != null) {
            if (page == null) {
                page = addPage(pageNumber, chapterNumber);
            }
            reference.setPage(page);
            page.addReference(reference);
        }
        return page;
    }

    @Override
    public Page removeReference(String pageNumber, String chapterNumber, PageReference reference) {
        PageReference existingReference = entityManager.find(PageReference.class, reference.getId());
        Page page = getPage(pageNumber, chapterNumber);
        if (!page.getReferences().isEmpty()) {
            page.getReferences().remove(existingReference);
        }
        return page;
    }

    @Override
    public Page getPage(String pageNumber, String chapterNumber) {
        Page existingPage;

        TypedQuery<Page> query = entityManager.createQuery(
                "select a from " + Page.class.getSimpleName() + " a where a.pageNumber = :pageNumber AND a.chapterNumber = :chapterNumber", Page.class);
        try {
            existingPage = query.setParameter("pageNumber", pageNumber).setParameter("chapterNumber", chapterNumber).getSingleResult();
        } catch (NoResultException nre) {
            existingPage = null;
        }

        return existingPage;
    }
}
