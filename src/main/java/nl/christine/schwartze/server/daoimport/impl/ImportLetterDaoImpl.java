/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.daoimport.impl;

import nl.christine.schwartze.server.daoimport.ImportLetterDao;
import nl.christine.schwartze.server.modelimport.ImportLetter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ImportLetterDaoImpl implements ImportLetterDao {

    @PersistenceContext(unitName = "importPU")
    private EntityManager entityManager;

    @Override
    public List<ImportLetter> getLetters() {

        TypedQuery<ImportLetter> query = entityManager.createQuery(
                "select a from " + ImportLetter.class.getSimpleName()
                        + " a",
                ImportLetter.class);

        return query.getResultList();
    }
}
