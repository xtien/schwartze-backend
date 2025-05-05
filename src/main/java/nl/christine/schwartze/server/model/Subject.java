/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "subjects")
public class Subject {

    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String NAME = "name";

    @Id
    @JsonProperty(ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty(NAME)
    private String name;

    @Transient
    @JsonProperty(TEXT)
    private Text text;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "language")
    @JsonIgnore
    private Map<String, Title> title = new HashMap<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "language")
    @JsonIgnore
    private Map<String, Text> texts = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String subject) {
        this.name = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Map<String, Text> getTexts() {
        return texts;
    }

    public void setTexts(Map<String, Text> texts) {
        this.texts = texts;
    }

    public void setTitleText(String language, Title title) {
        if (this.title == null) {
            this.title = new HashMap<>();
        }
        this.title.put(language, title);
    }

    public void setTitle(Map<String, Title> title){
        this.title = title;
    }
    public Map<String, Title> getTitle() {
        return title;
    }
 }
