/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.CombinePersonResult;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.model.Text;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: christine
 * Date: 12/29/18 12:14 PM
 */
public interface PersonService {

    Person getPerson(int id);

    Person updatePerson(Person person);

    Person addPerson(Person person);

    CombinePersonResult getCombinePersons(int id1, int id2);

    CombinePersonResult putCombinePersons(int id1, int id2);

    List<Person> getPeople(List<Integer> ids);

    List<Person> getAllPeople();

    int deletePerson(int id);

    Text getText(int id);

    EditLinkResult editLink(EditLinkRequest request);
}
