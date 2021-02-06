/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.exception.LetterNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.Text;

import java.util.List;

public interface LetterDao {

    List<Letter> getLetters();

    void create(Letter letter);

    Letter getLetterForNumber(int letterNumber);

    Letter getLetterForId(int id);

    Letter updateLetterComment(int letterNumber, String text, String date);

    Letter addLetter(Letter letter);

    void deleteLetter(Letter letter) throws LetterNotFoundException;

    Text getText(int id);

    Letter getNextLetter(int letterNumber);

    Letter getPreviousLetter(int letterNumber);
}
