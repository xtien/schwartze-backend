/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.SubjectDao;
import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Component("subjectService")
public class SubjectServiceImpl implements SubjectService {

    @Value("${defaultlanguage}")
    private String defaultLanguage;

    @Autowired
    private SubjectDao subjectDao;

    @Override
    @Transactional("transactionManager")
    public List<Subject> getSubjects() {
        List<Subject> subjects = subjectDao.getSubjects();
        for (Subject s : subjects) {
            if (s.getTexts() != null && s.getTexts().size() == 0) {
                if (s.getText() != null) {
                    s.getText().setLanguage(defaultLanguage);
                    s.getTexts().put(defaultLanguage, s.getText());
                }
            }
        }
        return subjects;
    }

    @Override
    @Transactional("transactionManager")
    public List<Subject> addSubject(String name, String language) {
        subjectDao.addSubject(name, language);
        return subjectDao.getSubjects();
    }

    @Override
    @Transactional("transactionManager")
    public Subject getSubjectById(Integer subjectId, String language) {
        return subjectDao.getSubjectById(subjectId);
    }

    @Override
    @Transactional("transactionManager")
    public List<Subject> removeSubject(Integer id) {
        subjectDao.remove(id);
        return getSubjects();
    }
}
