package nl.christine.schwartze.server.controller.request;

public class PersonRequest {

    private int id;

    private String comment;

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }
}
