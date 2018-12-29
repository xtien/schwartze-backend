package nl.christine.schwartze.server.controller.result;

import nl.christine.schwartze.server.model.Person;

import java.util.List;

public class PeopleResult extends ApiResult {

    private List<Person> people;

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
