/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Letter;

import javax.validation.constraints.NotNull;

public class AddLetterRequest {

    @NotNull
    @JsonProperty("letter")
    private Letter letter;

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }
}
