/*
 * Copyright (c) 2025, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.SubjectService;
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
public class TestSubject {

    @Autowired
    private SubjectService subjectService;

    Subject subject = new Subject();
    String defaultLanguage = "en";
    String subjectName = "test subject";
    String textTitle = "test title";
    String textString = "test text1";
    String language = "en";

    @Test
    public void testAddSubject() {
        subject = createSubject();
        subjectService.addOrUpdate(subject, defaultLanguage);

        Subject result = subjectService.getSubjectById(subject.getId(), defaultLanguage);
        assertNotNull(result);
        assertEquals(textString, result.getTexts().get(language).getTextString());
    }

    @Test
    public void testDeleteSubject() {
        subject = createSubject();
        Text text = new Text();
        text.setTextString(textString);
        subject.getTexts().put("es", text);
        subjectService.addOrUpdate(subject, defaultLanguage);
        subjectService.removeSubject(subject.getId());

        Subject result = subjectService.getSubjectById(subject.getId(), "en");
        assertNull(result);

    }

    @Test
    public void testUpdateSubject() {
        subject = createSubject();
        subjectService.addOrUpdate(subject, defaultLanguage);

        Text text = new Text();
        text.setTextTitle("new title");
        text.setTextString("new text");
        text.setLanguage("es");
        subjectService.updateSubject(subject, text, "es");

        Subject result = subjectService.getSubjectById(subject.getId(), "es");
        assertNotNull(result);
        assertEquals("new text", result.getTexts().get("es").getTextString());
        assertEquals("new title", result.getTexts().get("es").getTextTitle());
    }

    @Test
    public void testAddText() {
        subject = createSubject();
        subjectService.addOrUpdate(subject, defaultLanguage);

        Subject result = subjectService.getSubjectById(subject.getId(), "es");
        assertNotNull(result);
        assertEquals(1, result.getTexts().size());
        Text text = new Text();
        text.setTextTitle("new title");
        text.setTextString("new text");
        text.setLanguage("es");

        subject.getTexts().put("es", text);
        assertNotNull(result);
        assertEquals(1, result.getTexts().size());
    }

    @Test
    public void testUpdateText() {
        subject = createSubject();
        subjectService.addOrUpdate(subject, defaultLanguage);
        Subject result = subjectService.getSubjectById(subject.getId(), "es");
        assertNotNull(result);
        assertEquals(1, result.getTexts().size());

        Text text = new Text();
        text.setTextTitle("new title");
        text.setTextString("new text");
        text.setLanguage("en");
        Text text2 = new Text();
        text2.setTextTitle("nuevo titulo");
        text2.setTextString("nuevo texto");
        text2.setLanguage("es");

        Subject updatedSubject = subjectService.updateSubject(subject, text, "en");
        updatedSubject = subjectService.updateSubject(subject, text2, "es");

        assertNotNull(updatedSubject);
        assertEquals(2, updatedSubject.getTexts().size());
        assertEquals("new text", updatedSubject.getTexts().get("en").getTextString());
        assertEquals("nuevo texto", updatedSubject.getTexts().get("es").getTextString());
        assertEquals("new title", updatedSubject.getTexts().get("en").getTextTitle());
        assertEquals("nuevo titulo", updatedSubject.getTexts().get("es").getTextTitle());
    }


    private Subject createSubject() {
        subject.setName(subjectName);
        Text text = new Text();
        text.setTextString(textString);
        text.setTextTitle(textTitle);
        text.setLanguage("en");
        subject.getTexts().put(text.getLanguage(), text);
        return subject;
    }

}
