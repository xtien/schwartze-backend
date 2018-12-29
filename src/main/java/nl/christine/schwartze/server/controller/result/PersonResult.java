package nl.christine.schwartze.server.controller.result;

import nl.christine.schwartze.server.model.Person;

public class PersonResult extends ApiResult {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
