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
    public void addPage(String pageNumber) {
        Page existingPage = getPage(pageNumber);
        if (existingPage == null) {
            Page page = new Page();
            page.setPageNumber(pageNumber);
            entityManager.persist(page);
        }
    }

    @Override
    public void removePage(String pageNumber) {
        Page existingPage = getPage(pageNumber);
        if (existingPage == null) {
            entityManager.remove(existingPage);
        }
    }

    @Override
    public void addReference(String pageNumber, PageReference reference) {
        Page page = getPage(pageNumber);
        reference.setPage(page);
        page.addReference(reference);
    }

    @Override
    public void removeReference(String pageNumber, PageReference reference) {
        Page page = getPage(pageNumber);
        if (page.getReferences().contains(reference)) {
            page.getReferences().remove(reference);
        }
    }

    @Override
    public Page getPage(String pageNumber) {
        Page existingPage;

        TypedQuery<Page> query = entityManager.createQuery(
                "select a from " + Page.class.getSimpleName() + " a where a.pageNumber = :pageNumber ", Page.class);
        try {
            existingPage = query.setParameter("pageNumber", pageNumber).getSingleResult();
        } catch (NoResultException nre) {
            existingPage = null;
        }

        return existingPage;
    }
}
