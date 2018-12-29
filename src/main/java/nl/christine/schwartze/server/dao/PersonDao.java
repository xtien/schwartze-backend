package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.model.Person;

import java.util.List;

public interface PersonDao {

    void store(Person fromPerson);

    Person getPerson(int id);

    List<Person> getPersons();

    Person getPersonByName(Person person);

    List<Person> getPeople(List<Integer> ids);
}
