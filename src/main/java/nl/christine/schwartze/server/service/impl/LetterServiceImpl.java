/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.daoimport.ImportLetterDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import nl.christine.schwartze.server.service.LetterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class LetterServiceImpl implements LetterService {

    Logger logger = Logger.getLogger(LetterServiceImpl.class);

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private ImportLetterDao importLetterDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LocationDao locationDao;

    Logger log = Logger.getLogger(LetterServiceImpl.class);

    /**
     * Clearing db, not importdb, so use transactionManager
     *
     * @return
     */
    @Override
    @Transactional("transactionManager")
    public int clearTables() {
        try {
            deleteLetters(getLetters());
        } catch (Exception e) {
            logger.error("Error clearing tables", e);
            return -1;
        }
        return 0;
    }

    void deleteLetters(List<Letter> letters) {
        letterDao.deleteLetters(letters);
    }

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLetters() {
        List<Letter> letters = letterDao.getLetters();
        return letters;
    }

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLettersToPerson(int toId) {
        return personDao.getLettersForPerson(Optional.empty(), Optional.ofNullable(toId));
    }

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLettersFromPerson(int fromId) {
        return personDao.getLettersForPerson(Optional.ofNullable(fromId), Optional.empty());
    }

    /**
     * Use transactionManager because we are going to save the letters in the db, not in the import-db
     *
     * @param importLetter
     * @return
     */
    @Override
    @Transactional("transactionManager")
    public int persist(ImportLetter importLetter) {

        int result = 0;

        Letter letter = new Letter(importLetter.getNumber());
        letter.setComment(importLetter.getComment());
        letter.setDate(importLetter.getDate());

        if (importLetter.hasSender()) {

            String[] s = importLetter.getSender().split(",");
            for (String fromPersonString : s) {
                Person fromPerson = doPerson(fromPersonString);
                fromPerson.addLetterWritten(letter);
                letter.setSender(fromPerson);
            }
        }

        if (importLetter.hasRecipient()) {
            String[] s = importLetter.getRecipient().split(",");
            for (String toPersonString : s) {
                Person toPerson = doPerson(toPersonString);
                toPerson.addLetterReceived(letter);
                letter.setRecipient(toPerson);
            }
        }

        if (importLetter.hasFromLocation()) {
            MyLocation fromLocation = doLocation(importLetter.getFromLocation());
            fromLocation.setLetterFrom(letter);
            letter.setFromLocation(fromLocation);
        }

        if (importLetter.hasToLocation()) {
            MyLocation toLocation = doLocation(importLetter.getToLocation());
            toLocation.setLetterTo(letter);
            letter.setToLocation(toLocation);
        }

        /*
         * create, not update, because whatever the fields and keys are, it's a new letter from
         * the stream of imported letters
         */
        letterDao.create(letter);

        return result;
    }

    @Override
    @Transactional("transactionManager")
    public void persistIfNotPresent(ImportLetter importLetter) {

        Letter existingLetter = letterDao.getLetter(importLetter.getNumber());
        if (existingLetter == null) {
            persist(importLetter);
        }
    }

    @Override
    @Transactional("transactionManager")
    public Letter getLetterByNumber(int letterNumber) {
        return letterDao.getLetter(letterNumber);
    }

    @Override
    @Transactional("transactionManager")
    public Letter updateLetterComment(int letterNumber, String text) {
        Letter letter = null;
        try {
           letter = letterDao.updateLetterComment(letterNumber, text);
        } catch (Exception e) {
            logger.error("Error updating letter comments", e);
        }
        return letter;
    }

    private MyLocation doLocation(String location) {
        MyLocation fromLocation = createLocation(location);
        MyLocation existingLocation = locationDao.getLocationByName(fromLocation);
        if (existingLocation == null) {
            existingLocation = fromLocation;
        }
        return existingLocation;
    }

    private Person doPerson(String fromPersonString) {
        Person fromPerson = createPerson(fromPersonString);
        Person existingPerson = personDao.getPersonByName(fromPerson);
        if (existingPerson == null) {
            existingPerson = fromPerson;
        }
        return existingPerson;
    }

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

    private MyLocation createLocation(String fromLocation) {
        fromLocation = fromLocation.replaceAll("  ", " ").trim();
        return new MyLocation(fromLocation);
    }

    private Person createPerson(String importPerson) {

        importPerson = importPerson.replaceAll("  ", " ").trim();
        Person person = new Person();

        String[] str = importPerson.split(" ");
        int i = 0;
        if (str.length > 0) {
            person.setFirstName(str[0].trim());
            i = str[0].length();
            if (str.length > 2 && Character.isUpperCase(str[1].charAt(0))) {
                person.setMiddleName(str[1].trim());
                i += str[1].length() + 1;
            }
            person.setLastName(importPerson.substring(i, importPerson.length()).trim());
        }

        person.noNulls();

        return person;
    }
}
