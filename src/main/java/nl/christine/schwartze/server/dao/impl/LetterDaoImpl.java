/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.exception.LetterNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Component("letterDao")
public class LetterDaoImpl implements LetterDao {

    Query deletePeople;
    Query deleteLocations;
    Query deleteLetters;

    Logger logger = Logger.getLogger(LetterDaoImpl.class);

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;


    @Override
    public List<Letter> getLetters() {

        TypedQuery<Letter> query = em.createQuery(
                "select a from " + Letter.class.getSimpleName()
                        + " a order by a.number",
                Letter.class);

        return query.getResultList();
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

    private Letter getLetterForId(int id) {
        return em.find(Letter.class, id);
    }

    @Override
    public int deleteLetters(List<Letter> letters) {

        try {
            for (Letter l : letters) {
                Letter letter = em.find(Letter.class, l.getId());
                em.remove(letter);
            }
        } catch (Exception e) {
            logger.error("Error deleting letters", e);
            return -1;
        }

        return 0;
    }

    @Override
    public Letter updateLetterComment(int letterNumber, String text) {
        Letter letter = getLetterForNumber(letterNumber);
        letter.setComment(text);
        return letter;
    }

    @Override
    public Letter addLetter(Letter letter) {

        if (letter.getNumber() > 0) {
            return null;
        }
        em.persist(letter);

        return letter;
    }

    @Override
    public void deleteLetter(Letter letter) throws LetterNotFoundException {
        Letter existingLetter = getLetterForId(letter.getId());
        if (existingLetter != null) {
            em.remove(existingLetter);
        } else {
            throw new LetterNotFoundException();
        }
    }
}
