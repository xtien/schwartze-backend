package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: christine
 * Date: 12/29/18 12:15 PM
 */
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    Logger logger = Logger.getLogger(PersonServiceImpl.class);

    @Override
    public Person getPerson(int id) {
        return personDao.getPerson(id);
    }

    @Override
    @Transactional("transactionManager")
    public int updatePersonComment(Person person) {
        int result = -1;
        try {
            Person existingPerson = personDao.getPerson(person.getId());
            existingPerson.setComment(person.getComment());
            result = 0;
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }
}
