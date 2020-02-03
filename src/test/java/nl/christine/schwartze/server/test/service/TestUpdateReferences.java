/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.References;
import nl.christine.schwartze.server.service.ReferenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/testApplicationContext.xml"})
public class TestUpdateReferences {

    @Autowired
    private ReferenceService referenceService;
    private String linkName = "link name";

    @Test
    public void testUpdateReferences() {

        References references = new References();
        references.setType(("site"));
        References existingReferences = referenceService.updateReferences(references);
        assertNotNull(existingReferences);

        Link link = new Link();
        link.setLinkUrl("http://nothing");
        link.setLinkName((linkName));
        existingReferences.addLink(link);

        References references2 = referenceService.updateReferences(existingReferences);

        assertEquals(linkName, references2.getLinks().get(0).getLinkName());
    }
}
