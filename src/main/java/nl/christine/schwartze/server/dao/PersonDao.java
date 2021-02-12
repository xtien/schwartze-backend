/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.model.Text;

import java.util.List;
import java.util.Optional;

public interface PersonDao {

    Person getPerson(int id);

    List<Person> getPersons();

    Person getPersonByName(Person person);

    List<Person> getPeople(List<Integer> ids);

    List<Letter> getLettersForPerson(Optional<Integer> fromId, Optional<Integer> toId);

    Person addNewPerson(Person person);

    Person updatePerson(Person person);

    List<Person> getAllPeople();

    void deletePerson(Person person);

    void deletePerson(int id);

    void merge(Person person);

    Text getPersonText(int id);

    void persistIfNotExist(Person p);

    void savePerson(Person p);

    Person persist(Person person);
}
