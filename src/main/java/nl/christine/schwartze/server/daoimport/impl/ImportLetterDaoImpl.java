/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.daoimport.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import nl.christine.schwartze.server.daoimport.ImportLetterDao;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("importLetterDao")
@Profile("test")
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
