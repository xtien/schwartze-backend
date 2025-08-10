/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import com.deepl.api.DeepLException;
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

    @Autowired
    private TranslateServiceImpl translateService;

    @Override
    @Transactional("transactionManager")
    public List<Subject> getSubjects(String language) throws DeepLException, InterruptedException {
        return subjectDao.getSubjects();
    }

    @Override
    @Transactional("transactionManager")
    public Subject addOrUpdate(Subject subject, String language) {
        subject.getTexts().put(language, subject.getText());
        return subjectDao.addSubject(subject);
    }

    @Override
    @Transactional("transactionManager")
    public Subject updateSubject(Subject subject, Text text, String language) {
        return subjectDao.updateSubject(subject, text, language);
    }

    @Override
    @Transactional("transactionManager")
    public Subject getSubjectById(Integer subjectId, String language) throws DeepLException, InterruptedException {
        Subject subject = subjectDao.getSubjectById(subjectId);
        if (subject == null) {
            return null;
        }
        return convertSubjectTextForLanguage(subject, language);
    }

    @Override
    @Transactional("transactionManager")
    public void removeSubject(Integer id) {
        subjectDao.remove(id);
    }

    private List<Subject> convertText(List<Subject> subjects, String language) throws DeepLException, InterruptedException {
        return subjects.stream().map(s -> {
            try {
                return convertSubjectTextForLanguage(s, language);
            } catch (DeepLException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private Subject convertSubjectTextForLanguage(Subject s, String language) throws DeepLException, InterruptedException {
        if (s.getTexts().get(language) != null) {
            s.setText(s.getTexts().get(language));
            if (s.getTitle() != null && s.getTitle().get(language) != null) {
                s.setName(s.getTitle().get(language).getText());
            }
        } else if (s.getTexts().containsKey(defaultLanguage)) {
            Text t = s.getTexts().get(defaultLanguage);
            Text newText = new Text();
            if (t.getTextString() != null && !t.getTextString().isEmpty()) {
                newText.setTextString(translateService.translate(t.getTextString(), language));
            }
            if (t.getTextTitle() != null && !t.getTextTitle().isEmpty()) {
                newText.setTextTitle(translateService.translate(t.getTextTitle(), language));
            }
            newText.setLanguage(language);
            subjectDao.persist(newText);
            s.getTexts().put(language, newText);
            s.setText(newText);
        } else if (s.getTexts().size() > 0) {
            s.setText(s.getTexts().get(s.getTexts().values().toArray()[0]));
        }
        return s;
    }
}
