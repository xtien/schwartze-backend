package nl.christine.schwartze.server.daoimport;

import nl.christine.schwartze.server.modelimport.ImportLetter;

import java.util.List;

public interface ImportLetterDao {

    List<ImportLetter> getLetters();
}
