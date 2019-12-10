/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.dao.TextDao;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.TextService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("textService")
public class TextServiceImpl implements TextService {

    @Autowired
    private TextDao textDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LocationDao locationDao;

    @Override
    @Transactional("transactionManager")
    public Text addText() {
        return textDao.addText();
    }

    @Override
    @Transactional("transactionManager")
    public Text updateText(TextRequest request) {

        if (request.getPersonId()!=null) {
            Person person = personDao.getPerson(request.getPersonId());
            person.getText().setText(request.getText().getTextString());
            return person.getText();
        }

        if (request.getLocationId() != null) {
            MyLocation location = locationDao.getLocation(request.getLocationId());
            location.getText().setText(request.getText().getTextString());
            return location.getText();
        }

        return null;
    }
}
