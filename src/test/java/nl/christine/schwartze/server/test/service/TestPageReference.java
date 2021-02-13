/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.model.PageReference;
import nl.christine.schwartze.server.model.ReferenceType;
import nl.christine.schwartze.server.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
public class TestPageReference {

    private String pageNumber = "12";
    private String key = "key";
    private String description = "123 description ";
    private ReferenceType type = ReferenceType.LETTER;

    @Autowired
    private PageService pageService;

    @Test
    public void testAddPage() {

        pageService.addPage(pageNumber);

        Page page = pageService.getPage(pageNumber);

        assertNotNull(page);
        assertEquals(pageNumber, page.getPageNumber());

        pageService.removePage(pageNumber);
    }

    @Test
    public void testAddReference() {

        pageNumber = "13";

        Page page = new Page();
        page.setPageNumber(pageNumber);
        pageService.addPage(pageNumber);
        Page newPage = pageService.getPage(pageNumber);

        PageReference pageReference = new PageReference();
        pageReference.setKey(key);
        pageReference.setType(type);
        pageReference.setDescription(description);
        pageReference.setPage(page);
        pageService.addPageReference(pageNumber, pageReference);

        Page resultPage = pageService.getPage(pageNumber);
        assertNotNull(page);
        assertEquals(pageNumber, resultPage.getPageNumber());
        assertTrue(resultPage.getReferences().size() == 1);
        assertTrue(resultPage.getReferences().contains(pageReference));

        pageService.removePageReference(pageNumber, pageReference);

        resultPage = pageService.getPage(pageNumber);
        assertNotNull(resultPage);
        assertFalse(resultPage.getReferences().contains(pageReference));
    }
}
