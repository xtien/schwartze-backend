/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.daoimport.ImportLetterDao;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import nl.christine.schwartze.server.service.ImportLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("importLetterService")
@Profile("test")
public class ImportLetterServiceImpl implements ImportLetterService {

    @Autowired
    private ImportLetterDao importLetterDao;

    /**
     * getting letters from import db, so use importTransactionManager
     *
     * @return
     */
    @Override
    @Transactional("importTransactionManager")
    public List<ImportLetter> getImportLetters() {
        return importLetterDao.getLetters();
    }
}
