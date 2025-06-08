/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Letter;

public class LetterRequest {

    @JsonProperty("number")
    int letterNumber;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("letter")
    private Letter letter;

    @JsonProperty("date")
    private String date;

    @JsonProperty("language")
    private String language;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
