/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Letter;

public class AddLetterResult {

    @JsonProperty("letter")
    private Letter letter;

    @JsonProperty("text")
    private String text;

    public void setLetter(Letter resultLetter) {
        this.letter = resultLetter;
    }

    public void setErrorText(String text) {
        this.text = text;
    }

    public Letter getLetter() {
        return letter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
