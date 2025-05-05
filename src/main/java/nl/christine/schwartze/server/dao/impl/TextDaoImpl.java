/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.TextDao;
import nl.christine.schwartze.server.model.Text;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component("textDao")
public class TextDaoImpl implements TextDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager entityManager;

    @Override
    public Text addText() {
        Text text = new Text();
        entityManager.persist(text);
        return text;
    }

    @Override
    public Text getText(int id) {
        return entityManager.find(Text.class, id);
    }

    @Override
    public Text updateText(Text text) {
        Text existingText = getText(text.getId());
        if(existingText !=null){
            existingText.setTextString(text.getTextString());
        }
        return existingText;
    }

    @Override
    public void persist(Text text) {
        entityManager.persist(text);
    }
}
