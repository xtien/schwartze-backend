package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.model.Person;

/**
 * User: christine
 * Date: 12/29/18 12:14 PM
 */
public interface PersonService {

    Person getPerson(int id);

    int updatePerson(Person person);
}
