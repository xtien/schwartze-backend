/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.TextDao;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("textService")
public class TextServiceImpl implements TextService {

    @Autowired
    private TextDao textDao;

    @Override
    @Transactional("transactionManager")
    public Text addText() {
        return textDao.addText();
    }

    @Override
    @Transactional("transactionManager")
    public Text updateText(Text text) {
        return textDao.updateText(text);
    }
}
