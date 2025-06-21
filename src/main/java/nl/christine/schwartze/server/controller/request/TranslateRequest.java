/*
 * Copyright (c) 2025, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslateRequest {

    @JsonProperty("id")
    private int number;

    @JsonProperty("language")
    private String language;

    public int getNumber() {
        return number;
    }

    public void setNumber(int letterNnumberumber) {
        this.number = number;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
