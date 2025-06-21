/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.SubjectDao;
import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("subjectService")
public class SubjectServiceImpl implements SubjectService {

    Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Value("${defaultlanguage}")
    private String defaultLanguage;

    @Autowired
    private SubjectDao subjectDao;

    @Override
    @Transactional("transactionManager")
    public List<Subject> getSubjects(String language) {
        return convertText(subjectDao.getSubjects(), language);
    }

    @Override
    @Transactional("transactionManager")
    public Subject addSubject(Subject subject) {
        return subjectDao.addSubject(subject);
    }

    @Override
    @Transactional("transactionManager")
    public Subject updateSubject(Subject subject, Text text, String language) {
        return subjectDao.updateSubject(subject,  text, language);
    }

    @Override
    @Transactional("transactionManager")
    public Subject getSubjectById(Integer subjectId, String language) {
        Subject  subject = subjectDao.getSubjectById(subjectId);

        if(subject == null){
            return null;
        }
        return getSubjectTextForLanguage(subject, language);

    }

    @Override
    @Transactional("transactionManager")
    public void removeSubject(Integer id) {
        subjectDao.remove(id);
    }

    private List<Subject> convertText(List<Subject> subjects, String language) {
        return subjects.stream().map(s -> getSubjectTextForLanguage(s, language)).collect(Collectors.toList());
    }

    private Subject getSubjectTextForLanguage(Subject s, String language) {
        if (s.getTexts().get(language) != null) {
            s.setText(s.getTexts().get(language));
        } else if(s.getTexts().containsKey(defaultLanguage)) {
            s.setText(s.getTexts().get(defaultLanguage));
        } else if(s.getTexts().size()>0){
            s.setText(s.getTexts().get(s.getTexts().values().toArray()[0]));
        }

        return s;
    }
}
