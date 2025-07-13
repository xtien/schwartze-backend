/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import nl.christine.schwartze.server.dao.SubjectDao;
import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.model.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("subjectDao")
public class SubjectDaoImpl implements SubjectDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    @Override
    public List<Subject> getSubjects() {
        List<Subject> subjects = entityManager.createQuery(
                "select a from " + Subject.class.getSimpleName()
                        + " a order by a.name",
                Subject.class).getResultList();
        return subjects;
    }

    @Override
    public Subject addSubject(Subject subject) {

        Subject existingSubject;

        try {
            existingSubject = entityManager.createQuery("select a from " + Subject.class.getSimpleName()
                            + " a where a.id = :id",
                    Subject.class).setParameter(Subject.ID, subject.getId()).getSingleResult();
            existingSubject.setName(subject.getName());
            if (subject.getTexts() != null && subject.getTexts().size() > 0) {
                for (Text text : subject.getTexts().values()) {
                    if (existingSubject.getTexts().containsKey(text.getLanguage())) {
                        existingSubject.getTexts().get(text.getLanguage()).setTextString(text.getTextString());
                    } else {
                        existingSubject.getTexts().put(text.getLanguage(), text);
                    }
                }
            }
            if (subject.getTitles() != null && subject.getTitles().size() > 0) {
                for (Title title : subject.getTitles().values()) {
                    if (existingSubject.getTitles().containsKey(title.getLanguage())) {
                        existingSubject.getTitles().get(title.getLanguage()).setText(title.getText());
                    } else {
                        existingSubject.getTitles().put(title.getLanguage(), title);
                    }
                }
            }
            if (subject.getTitles() != null && subject.getTitles().size() > 0) {
                existingSubject.getTitles().putAll(subject.getTitles());
            }
        } catch (NoResultException nre) {
            if (subject.getText() != null) {
                entityManager.persist(subject.getText().clone());
                subject.getTexts().put(subject.getText().getLanguage(), subject.getText());
            }
            entityManager.persist(subject);
            return subject;
        }

        return existingSubject;
    }

    @Override
    public Subject updateSubject(Subject subject, Text text, String language) {

        Subject existingSubject = null;

        try {
            existingSubject = entityManager.createQuery("select a from " + Subject.class.getSimpleName()
                            + " a where a.id = :id",
                    Subject.class).setParameter(Subject.ID, subject.getId()).getSingleResult();

            if (existingSubject.getText() != null) {
                existingSubject.getTexts().put(existingSubject.getText().getLanguage(), existingSubject.getText());
                existingSubject.setText(null);
            }
            existingSubject.setName(subject.getName());
            if (subject.getTexts() != null) {
                existingSubject.getTexts().putAll(subject.getTexts());
            }
            if (subject.getTitles() != null) {
                existingSubject.setTitles(subject.getTitles());
            }
            subject.getTexts().put(language, text);
            entityManager.persist(text);
            entityManager.merge(subject);

        } catch (NoResultException nre) {
            subject = new Subject();
            if (text != null) {
                subject.getTexts().put(language, text);
                subject.setText(text);
            }
            entityManager.persist(subject.getText());
            entityManager.persist(subject);
            entityManager.persist(subject.getTitles());
            return existingSubject;
        }

        return subject;
    }

    @Override
    public Subject getSubjectById(Integer subjectId) {
        return entityManager.find(Subject.class, subjectId);
    }

    @Override
    public void remove(Integer id) {
        Subject subject = entityManager.find(Subject.class, id);
        if (subject != null) {
            if (subject.getTexts() != null && subject.getTexts().size() > 0) {
                for (Text text : subject.getTexts().values()) {
                    entityManager.remove(text);
                }
                subject.setTexts(null);
            }
            if (subject.getText() != null) {
                entityManager.remove(subject.getText());
                subject.setText(null);
            }
            entityManager.remove(subject);
        }
    }

    @Override
    public void persist(Text text) {
        entityManager.persist(text);
    }

    @Override
    public void persist(Title title) {
        entityManager.persist(title);
    }
}
