/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.model.Text;

import java.util.List;

public interface SubjectService {

    List<Subject> getSubjects(String language);

    Subject addOrUpdate(Subject subject, String language);

    Subject updateSubject(Subject subject, Text text, String language);

    Subject getSubjectById(Integer subjectId, String language);

    void removeSubject(Integer id);
}
