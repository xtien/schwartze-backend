/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

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
