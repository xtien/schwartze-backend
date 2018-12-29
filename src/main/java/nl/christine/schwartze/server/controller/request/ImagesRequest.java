package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImagesRequest {

    @JsonProperty("number")
    int letterNumber;

    public int getLetterNumber() {
        return letterNumber;
    }

    public void setNumber(int number) {
        this.letterNumber = number;
    }
}