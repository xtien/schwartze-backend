/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.SubjectDao;
import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.model.Text;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("subjectDao")
public class SubjectDaoImpl implements SubjectDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    Logger logger = Logger.getLogger(SubjectDaoImpl.class);

    @Override
    public List<Subject> getSubjects() {
        return entityManager.createQuery(
                "select a from " + Subject.class.getSimpleName()
                        + " a order by a.name",
                Subject.class).getResultList();
    }

    @Override
    public Subject addSubject(String name) {

        Subject subject = null;

        try {
            subject = entityManager.createQuery("select a from " + Subject.class.getSimpleName()
                            + " a where a.name = :name",
                    Subject.class).setParameter(Subject.NAME, name).getSingleResult();
        } catch (NoResultException nre) {
            subject = new Subject();
            subject.setText(new Text());
            entityManager.persist(subject.getText());
            entityManager.persist(subject);
        }
        subject.setName(name);
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
            entityManager.remove(subject);
        }
    }

    @Override
    public void persist(Text text) {
        entityManager.persist(text);
    }
}
