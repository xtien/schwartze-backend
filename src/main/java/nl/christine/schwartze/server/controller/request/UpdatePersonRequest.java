package nl.christine.schwartze.server.controller.request;

import nl.christine.schwartze.server.model.Person;

public class UpdatePersonRequest {

    public UpdatePersonRequest() {

    }

    private int id;

    private Person person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
