/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.enums.LettersOrderByEnum;
import nl.christine.schwartze.server.dao.CollectieDao;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Comparator<Letter> compareByFirstName;
    private final Comparator<Letter> compareByLastName;

    Logger logger = LoggerFactory.getLogger(LetterServiceImpl.class);

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private CollectieDao collectieDao;

    public LetterServiceImpl() {
        compareByDate = Comparator
                .comparing(Letter::getDate, Comparator.nullsFirst(Comparator.naturalOrder()));
        compareByNumber = Comparator
                .comparing(Letter::getNumber, Comparator.nullsFirst(Comparator.naturalOrder()));
        compareByFirstName = (o1, o2) -> {
            if (o1.getSenders() == null || o1.getSenders().isEmpty()) {
                return 1;
            }
            if (o2.getSenders() == null || o2.getSenders().isEmpty()) {
                return -1;
            }
            if (o1.getSenders().get(0).getFirstName() == null) {
                return 1;
            }
            if (o2.getSenders().get(0).getFirstName() == null) {
                return -1;
            }
            if (o1.getSenders().get(0).getFirstName().equals(o2.getSenders().get(0).getFirstName())) {
                return 0;
            } else {
                return o1.getSenders().get(0).getFirstName().compareTo(o2.getSenders().get(0).getFirstName());
            }
        };
        compareByLastName = (o1, o2) -> {
            if (o1.getSenders() == null || o1.getSenders().isEmpty()) {
                return 1;
            }
            if (o2.getSenders() == null || o2.getSenders().isEmpty()) {
                return -1;
            }
            if (o1.getSenders().get(0).getLastName() == null) {
                return 1;
            }
            if (o2.getSenders().get(0).getLastName() == null) {
                return -1;
            }
            if (o1.getSenders().get(0).getLastName().equals(o2.getSenders().get(0).getLastName())) {
                return 0;
            } else {
                return o1.getSenders().get(0).getLastName().compareTo(o2.getSenders().get(0).getLastName());
            }
        };
    }

    @Transactional("transactionManager")
    @Override
    public List<Letter> getLetters(LettersOrderByEnum order) {
        Comparator<Letter> comparator = selectComparator(order);
        return Optional.ofNullable(letterDao.getLetters()).orElse(new ArrayList<>()).stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLettersForPerson(int id, LettersOrderByEnum orderBy) {
        return personDao.getLettersForPerson(id).stream().sorted(compareByDate).collect(Collectors.toList());
    }

    @Override
    @Transactional("transactionManager")
    public List<Letter> getLettersForLocation(int locationId) {
        return locationDao.getLettersForLocation(Optional.ofNullable(locationId)).stream().sorted(compareByDate).collect(Collectors.toList());
    }

    @Override
    @Transactional("transactionManager")
    public Text getText(int id) {
        return letterDao.getText(id);
    }


    @Override
    @Transactional("transactionManager")
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
     * @param newLocations
     */
    private void updateLocations(List<MyLocation> existingLocations, List<MyLocation> newLocations, Letter existingLetter) {
        List<MyLocation> toRemove = new ArrayList<>();
        List<MyLocation> toAdd = new ArrayList<>();

        for (MyLocation location : existingLocations) {
            if (!newLocations.contains(location)) {
                MyLocation location1 = locationDao.getLocation(location.getId());
                if (location1.getLettersFrom().contains(existingLetter)) {
                    location1.getLettersFrom().remove(existingLetter);
                }
                if (location1.getLettersTo().contains(existingLetter)) {
                    location1.getLettersTo().remove(existingLetter);
                }
                toRemove.add(location);
            }
        }
        for (MyLocation location : newLocations) {
            if (!existingLocations.contains(location)) {
                MyLocation ll = locationDao.getLocation(location.getId());
                toAdd.add(ll);
            }
        }
        existingLocations.removeAll(toRemove);
        for (MyLocation location : toAdd) {
            locationDao.persistIfNotExist(location);
            existingLocations.add(location);
        }
    }

    /**
     * for now, assume there is only one sender and one recipient
     *
     * @param existingPeople
     */
    private void updateSendersRecipients(List<Person> existingPeople, List<Person> newPeople, Letter existingLetter) {
        if (!CollectionUtils.isEmpty(newPeople)) {
            List<Person> toAdd = new ArrayList<>();
            List<Person> toRemove = new ArrayList<>();
            for (Person p : newPeople) {
                if (!existingPeople.contains(p)) {
                    Person pp = personDao.getPerson(p.getId());
                    toAdd.add(pp);
                }
            }
            for (Person p : existingPeople) {
                if (!newPeople.contains(p)) {
                    Person existingP = personDao.getPerson(p.getId());
                    if (existingP.getLettersWritten().contains(existingLetter)) {
                        existingP.getLettersWritten().remove(existingLetter);
                    }
                    if (existingP.getLettersReceived().contains(existingLetter)) {
                        existingP.getLettersReceived().remove(existingLetter);
                    }
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

        if (letter.getCollectie() != null) {
            letter.setCollectie(collectieDao.getCollectie(letter.getCollectie().getId()));
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
    public Letter getNextLetter(int letterNumber) {
        return letterDao.getNextLetter(letterNumber);
    }

    @Override
    @Transactional("transactionManager")
    public Letter getPreviousLetter(int letterNumber) {
        return letterDao.getPreviousLetter(letterNumber);
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
        fromLocation = fromLocation.replace("  ", " ").trim();
        return new MyLocation(fromLocation);
    }

    private Person createPerson(String importPerson) {

        importPerson = importPerson.replace("  ", " ").trim();
        Person person = new Person();

        String[] str = importPerson.split(" ");
        int i = 0;
        if (str.length > 0) {
            person.setFirstName(str[0].trim());
            i = str[0].length();
            if (str.length > 2 && Character.isUpperCase(str[1].charAt(0))) {
                person.setFullName(str[1].trim());
                i += str[1].length() + 1;
            }
            person.setLastName(importPerson.substring(i, importPerson.length()).trim());
        }

        person.noNulls();

        return person;
    }

    private Comparator<Letter> selectComparator(LettersOrderByEnum order) {
        Comparator<Letter> comparator;
        switch (order) {
            case DATE -> {
                comparator = compareByDate;
            }
            case SENDER_FIRSTNAME -> {
                comparator = compareByFirstName;
            }
            case SENDER_LASTNAME -> {
                comparator = compareByLastName;
            }
            default -> comparator = compareByNumber;
        }
        return comparator;
    }
}
