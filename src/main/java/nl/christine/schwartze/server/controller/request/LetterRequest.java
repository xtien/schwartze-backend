package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LetterRequest {

    @JsonProperty("number")
    int letterNumber;

    @JsonProperty("comment")
    private String comment;

    public int getLetterNumber() {
        return letterNumber;
    }

    public void setNumber(int number) {
        this.letterNumber = number;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}