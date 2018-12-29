package nl.christine.schwartze.server.daoimport.impl;

import nl.christine.schwartze.server.daoimport.ImportLetterDao;
import nl.christine.schwartze.server.modelimport.ImportLetter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ImportLetterDaoImpl implements ImportLetterDao {

    @PersistenceContext(unitName = "importPU")
    private EntityManager entityManager;

    public ImportLetterDaoImpl() {

    }

    @Override
    public List<ImportLetter> getLetters() {

        TypedQuery<ImportLetter> query = entityManager.createQuery(
                "select a from " + ImportLetter.class.getSimpleName()
                        + " a",
                ImportLetter.class);

        return query.getResultList();
    }
}
