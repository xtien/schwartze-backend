/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.exception.LetterNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.modelimport.ImportLetter;

import java.util.List;

public interface LetterService {

    int persist(ImportLetter importLetter);

    Letter updateLetterComment(int letterNumber, String text, String date);

    void persistIfNotPresent(ImportLetter importLetter);

    Letter getLetterByNumber(int i);

    List<Letter> getLetters();

    List<Letter> getLettersByDate();

    List<Letter> getLettersToPerson(int toId);

    List<Letter> getLettersFromPerson(int fromId);

    Letter addLetter(Letter letter);

    void deleteLetter(Letter letter) throws LetterNotFoundException;

    List<Letter> getLettersForLocation(int id);

    Text getText(int letterId);

    Letter getLetterById(Integer letterId);

    Letter updateLetter(Letter letter);
}
