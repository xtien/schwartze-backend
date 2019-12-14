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
    public Text updateText(TextRequest request) {

        if (request.getId() != null) {
            return textDao.updateText(request.getText());
        } else if (request.getPersonId() != null) {
            Person person = personDao.getPerson(request.getPersonId());
            if (person.getText() == null) {
                person.setText(new Text());
                textDao.persist(person.getText());
            }
            person.getText().setTextString(request.getText().getTextString());
            return person.getText();
        } else if (request.getLocationId() != null) {
            MyLocation location = locationDao.getLocation(request.getLocationId());
            if (location.getText() == null) {
                location.setText(new Text());
                textDao.persist(location.getText());
            }
            location.getText().setTextString(request.getText().getTextString());
            return location.getText();
        }

        return null;
    }

    @Override
    @Transactional("transactionManager")
    public Text getText(Integer id) {
        return textDao.getText(id);
    }
}
