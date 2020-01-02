/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.SubjectDao;
import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("subjectService")
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    @Override
    @Transactional("transactionManager")
    public List<Subject> getSubjects() {
       return subjectDao.getSubjects();
    }

    @Override
    @Transactional("transactionManager")
    public List<Subject> updateSubject(String name) {
        subjectDao.updateSubject(name);
        return subjectDao.getSubjects();
    }
}
