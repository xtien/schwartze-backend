/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.exception.LetterNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Text;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component("letterDao")
public class LetterDaoImpl implements LetterDao {

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;
    private int maxLetterNumber = 860;

    @Override
    public List<Letter> getLetters() {

        TypedQuery<Letter> query = em.createQuery(
                "select a from " + Letter.class.getSimpleName()
                        + " a order by a.number",
                Letter.class);

        List<Letter> result = query.getResultList();
        maxLetterNumber = result.isEmpty() ? 1 : result.get(result.size() - 1).getNumber();
        return result;
    }

    @Override
    public void create(Letter letter) {

        em.persist(letter);
    }

    @Override
    public Letter getLetterForNumber(int letterNumber) {

        TypedQuery<Letter> query = em.createQuery(
                "select a from " + Letter.class.getSimpleName()
                        + " a where " + Letter.NUMBER + " = :number",
                Letter.class);

        try {
            return query.setParameter(Letter.NUMBER, letterNumber).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Letter getNextLetter(int letterNumber) {
        Letter result = null;
        while (result == null && letterNumber <= maxLetterNumber) {
            result = getLetterForNumber(++letterNumber);
        }
        return result;
    }

    @Override
    public Letter getPreviousLetter(int letterNumber) {
        Letter result = null;
        while (result == null && letterNumber > 0) {
            result = getLetterForNumber(--letterNumber);
        }
        return result;
    }

    @Override
    public Letter getLetterForId(int id) {
        return em.find(Letter.class, id);
    }

    @Override
    public Letter updateLetterComment(int letterNumber, String text, String date) {
        Letter letter = getLetterForNumber(letterNumber);
        letter.setComment(text);
        letter.setDate(date);
        return letter;
    }

    @Override
    public Letter addLetter(Letter letter) {

        if (letter.getId() > 0) {
            return null;
        }

        em.persist(letter);

        return letter;
    }

    @Override
    public void deleteLetter(Letter letter) throws LetterNotFoundException {
        Letter existingLetter = getLetterForId(letter.getId());
        if (existingLetter != null) {
            existingLetter.getToLocations().clear();
            existingLetter.getFromLocations().clear();
            existingLetter.getRecipients().clear();
            existingLetter.getSenders().clear();
            em.remove(existingLetter);
        } else {
            throw new LetterNotFoundException();
        }
    }

    @Override
    public Text getText(int id) {
        Letter letter = getLetterForId(id);
        if (letter.getText() == null) {
            Text text = new Text();
            em.persist(text);
            letter.setText(text);
        }
        return letter.getText();
    }
}
