/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="title")
public class Title {

    public static final String ID = "id";
    private static final String TEXT = "text";
    private static final String LANGUAGE = "language";

    @Id
    @JsonProperty(ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty(LANGUAGE)
    private String language;

    @JsonProperty(TEXT)
    private String text;

    public Title(){

    }

    public Title(String language, String title) {
        this.language = language;
        this.text = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
