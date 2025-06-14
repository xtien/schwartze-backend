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

    Logger logger = LoggerFactory.getLogger(SubjectDaoImpl.class);

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

        Subject existingSubject = null;

        try {
            existingSubject = entityManager.createQuery("select a from " + Subject.class.getSimpleName()
                            + " a where a.id = :id",
                    Subject.class).setParameter(Subject.ID, subject.getId()).getSingleResult();
            existingSubject.setName(subject.getName());
            if (subject.getTexts() == null) {
                existingSubject.getTexts().putAll(subject.getTexts());
            }
            if (subject.getTitles() != null) {
                existingSubject.setTitles(subject.getTitles());
            }
            entityManager.merge(subject);
        } catch (NoResultException nre) {
            entityManager.persist(subject);
            return subject;
        }

        return subject;
    }

    @Override
    public Subject updateSubject(Subject subject, Text text, String language) {

        Subject existingSubject = null;

        try {
            existingSubject = entityManager.createQuery("select a from " + Subject.class.getSimpleName()
                            + " a where a.id = :id",
                    Subject.class).setParameter(Subject.ID, subject.getId()).getSingleResult();

            existingSubject.setName(subject.getName());
            if (subject.getTexts() == null) {
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
        Subject  subject = entityManager.find(Subject.class, id);
           if (subject != null) {
            if (subject.getTexts() != null && subject.getTexts().size() > 0) {
                for(Text text : subject.getTexts().values()) {
                    entityManager.remove(text);
                }
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
