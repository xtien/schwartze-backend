package nl.christine.schwartze.server.dao.impl;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.model.Letter;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class LetterDaoImpl implements LetterDao {

    Query deletePeople;
    Query deleteLocations;
    Query deleteLetters;

    Logger logger = Logger.getLogger(LetterDaoImpl.class);

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    public LetterDaoImpl() {
    }

    @Override
    public int clearTables() {

        deletePeople = em.createQuery("DELETE from Person");
        deleteLocations = em.createQuery("DELETE from MyLocation");
        deleteLetters = em.createQuery("DELETE from Letter");

        try {
            deletePeople.executeUpdate();
            deleteLocations.executeUpdate();
            deleteLetters.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    @Override
    public List<Letter> getLetters() {

        TypedQuery<Letter> query = em.createQuery(
                "select a from " + Letter.class.getSimpleName()
                        + " a order by a.number",
                Letter.class);

        return query.getResultList();
    }

    @Override
    public void create(Letter letter) {

        em.persist(letter);
    }

    @Override
    public Letter getLetter(int letterNumber) {

        TypedQuery<Letter> query = em.createQuery(
                "select a from " + Letter.class.getSimpleName()
                        + " a where " + Letter.NUMBER + " = :number",
                Letter.class);

        try {
            return query.setParameter(Letter.NUMBER, letterNumber).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int deleteLetters(List<Letter> letters) {

        try {
            for (Letter l : letters) {
                Letter letter = em.find(Letter.class, l.getId());
                em.remove(letter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return 0;

    }
}
