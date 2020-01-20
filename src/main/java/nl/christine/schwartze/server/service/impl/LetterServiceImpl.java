/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.exception.LetterNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import nl.christine.schwartze.server.service.LetterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component("letterService")
public class LetterServiceImpl implements LetterService {

    private final Comparator<Letter> compareByDate;
    private final Comparator<Letter> compareByNumber;

    Logger logger = Logger.getLogger(LetterServiceImpl.class);

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LocationDao locationDao;

    Logger log = Logger.getLogger(LetterServiceImpl.class);

    public LetterServiceImpl() {
        compareByDate = Comparator
                .comparing(Letter::getDate, Comparator.nullsFirst(Comparator.naturalOrder()));
        compareByNumber = Comparator
                .comparing(Letter::getNumber, Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLetters() {
        List<Letter> letters = letterDao.getLetters().stream().sorted(compareByNumber).collect(Collectors.toList());
        return letters;
    }

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLettersByDate() {
        List<Letter> letters = letterDao.getLetters().stream().sorted(compareByDate).collect(Collectors.toList());
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

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLettersFromLocation(int fromId) {
        return locationDao.getLettersForLocation(Optional.ofNullable(fromId), Optional.empty());
    }

    @Override
    @Transactional("transactionManager")
    public Text getText(int id) {
        return letterDao.getText(id);
    }


    @Override
    public Letter getLetterById(Integer letterId) {
        return letterDao.getLetterForId(letterId);
    }

    @Override
    @Transactional("transactionManager")
    public Letter updateLetter(Letter letter) {

        Letter existingLetter = letterDao.getLetterForNumber(letter.getNumber());
        if (existingLetter != null) {
            if (letter.getDate() != null) {
                existingLetter.setLocalDate(letter.getDate());
            }
            if (existingLetter.getSenders() != null) {
                updateSendersRecipients(existingLetter.getSenders(), letter.getSenders(), existingLetter);
            }
            if (existingLetter.getRecipients() != null) {
                updateSendersRecipients(existingLetter.getRecipients(), letter.getRecipients(), existingLetter);
            }
            if (existingLetter.getFromLocations() != null) {
                updateLocations(existingLetter.getFromLocations(), letter.getFromLocations(), existingLetter);
            }
            if (existingLetter.getToLocations() != null) {
                updateLocations(existingLetter.getToLocations(), letter.getToLocations(), existingLetter);
            }
        }
        return existingLetter;
    }

    /**
     * for now, assume there is only one location
     *
     * @param existingLocations
     * @param locations
     * @param existingLetter
     */
    private void updateLocations(List<MyLocation> existingLocations, List<MyLocation> locations, Letter existingLetter) {
        if (!CollectionUtils.isEmpty(locations)) {
            MyLocation newLocation = locations.get(0);
            if (newLocation !=null && newLocation.getId() != 0) {
                newLocation = locationDao.getLocation(newLocation.getId());
                existingLocations.clear();
                existingLocations.add(newLocation);
            }
        }
    }

    /**
     * for now, assume there is only one sender and one recipient
     *
     * @param existingPeople
     * @param people
     * @param letter
     */
    private void updateSendersRecipients(List<Person> existingPeople, List<Person> people, Letter letter) {
        if (!CollectionUtils.isEmpty(people)) {
            List<Person> toAdd = new ArrayList<>();
            List<Person> toRemove = new ArrayList<>();
            for (Person p : people) {
                if (!existingPeople.contains(p)) {
                    Person existingP = personDao.getPerson(p.getId());
                    existingP.getLettersWritten().add(letter);
                    toAdd.add(existingP);
                }
            }
            for (Person p : existingPeople) {
                if (!people.contains(p)) {
                    Person existingP = personDao.getPerson(p.getId());
                    existingP.getLettersWritten().remove(letter);
                    toRemove.add(p);
                }
            }
            existingPeople.removeAll(toRemove);
            for (Person p : toAdd) {
                personDao.persistIfNotExist(p);
            }
            existingPeople.addAll(toAdd);
        }
    }

    @Override
    @Transactional("transactionManager")
    public Letter addLetter(Letter letter) {

        Letter existingLetter = letterDao.getLetterForNumber(letter.getNumber());
        if (existingLetter != null) {
            return null;
        }

        List<Person> toAdd = new LinkedList<>();
        List<Person> toRemove = new LinkedList<>();
        for (Person person : letter.getRecipients()) {
            if (person.getId() == 0) {
                personDao.persist(person);
            } else {
                Person existingPerson = personDao.getPerson(person.getId());
                existingPerson.addLetterReceived(letter);
                toAdd.add(existingPerson);
                toRemove.add(person);
            }
        }
        letter.getRecipients().removeAll(toRemove);
        letter.getRecipients().addAll(toAdd);

        toAdd.clear();
        toRemove.clear();

        for (Person person : letter.getSenders()) {
            if (person.getId() == 0) {
                personDao.persist(person);
            } else {
                Person existingPerson = personDao.getPerson(person.getId());
                existingPerson.addLetterWritten(letter);
                toAdd.add(existingPerson);
                toRemove.add(person);
            }
        }

        letter.getSenders().removeAll(toRemove);
        letter.getSenders().addAll(toAdd);

        List<MyLocation> locationAdd = new LinkedList<>();
        List<MyLocation> lcoationRemove = new LinkedList<>();

        for (MyLocation location : letter.getFromLocations()) {
            if (location.getId() == 0) {
                locationDao.persist(location);
            } else {
                MyLocation existingLocation = locationDao.getLocation(location.getId());
                existingLocation.addLetterFrom(letter);
                locationAdd.add(existingLocation);
                lcoationRemove.add(location);
            }
        }

        letter.getFromLocations().removeAll(lcoationRemove);
        letter.getFromLocations().addAll(locationAdd);

        locationAdd.clear();
        lcoationRemove.clear();

        for (MyLocation location : letter.getToLocations()) {
            if (location.getId() == 0) {
                locationDao.persist(location);
            } else {
                MyLocation existingLocation = locationDao.getLocation(location.getId());
                existingLocation.addLetterTo(letter);
                locationAdd.add(existingLocation);
                lcoationRemove.add(location);
            }
        }
        letter.getToLocations().removeAll(lcoationRemove);
        letter.getToLocations().addAll(locationAdd);

        letterDao.addLetter(letter);
        return letter;
    }

    @Override
    @Transactional("transactionManager")
    public void deleteLetter(Letter letter) throws LetterNotFoundException {
        letterDao.deleteLetter(letter);
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
            fromLocation.addLetterFrom(letter);
            letter.addFromLocation(fromLocation);
        }

        if (importLetter.hasToLocation()) {
            MyLocation toLocation = doLocation(importLetter.getToLocation());
            toLocation.addLetterTo(letter);
            letter.addToLocation(toLocation);
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

        Letter existingLetter = letterDao.getLetterForNumber(importLetter.getNumber());
        if (existingLetter == null) {
            persist(importLetter);
        }
    }

    @Override
    @Transactional("transactionManager")
    public Letter getLetterByNumber(int letterNumber) {
        return letterDao.getLetterForNumber(letterNumber);
    }

    @Override
    @Transactional("transactionManager")
    public Letter updateLetterComment(int letterNumber, String text, String date) {
        Letter letter = null;
        try {
            letter = letterDao.updateLetterComment(letterNumber, text, date);
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
