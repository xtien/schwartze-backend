/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

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