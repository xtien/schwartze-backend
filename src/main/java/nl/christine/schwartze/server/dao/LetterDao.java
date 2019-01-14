package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.model.Letter;

import java.util.List;

public interface LetterDao {

    List<Letter> getLetters();

    void create(Letter letter);

    Letter getLetter(int letterNumber);

    int deleteLetters(List<Letter> letters);
}
