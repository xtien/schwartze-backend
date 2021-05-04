/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.model.Subject;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.model.Title;

import java.util.List;

public interface SubjectDao {

    List<Subject> getSubjects();

    Subject addSubject(String name, String language);

    Subject getSubjectById(Integer subjectId);

    void remove(Integer id);

    void persist(Text text);

    void persist(Title title);
}
