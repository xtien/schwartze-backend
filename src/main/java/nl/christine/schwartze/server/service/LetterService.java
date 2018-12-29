package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.modelimport.ImportLetter;

import java.util.List;

public interface LetterService {

    int clearTables();

    int persist(ImportLetter importLetter);

    List<ImportLetter> getImportLetters();

    void persistIfNotPresent(ImportLetter importLetter);

    Letter getLetterByNumber(int i);

    int updateLetterComments(Letter letter);
}
