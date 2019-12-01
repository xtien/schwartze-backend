/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class MyLocation {

    public static final String LOCATION_NAME = "location_name";
    public static final String DESCRIPTION = "description";
    public static final String LINKS = "links";
    public static final String COMMENT = "comment";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = LOCATION_NAME)
    @JsonProperty(LOCATION_NAME)
    private String locationName;

    @Column(name = DESCRIPTION)
    @JsonProperty(DESCRIPTION)
    private String description;

    @ManyToMany(mappedBy = "fromLocations", cascade = CascadeType.ALL)
    private List<Letter> lettersFrom = new ArrayList<>();

    @ManyToMany(mappedBy = "toLocations", cascade = CascadeType.ALL)
    private List<Letter> lettersTo = new ArrayList<>();

    @Column(name = LINKS)
    @ElementCollection(targetClass = String.class)
    private List<String> links = new ArrayList<>();

    @Column(name = COMMENT)
    @JsonProperty(COMMENT)
    private String comment;
    @OneToOne
    @JsonProperty("text")
    private Text text;

    public MyLocation() {
    }

    public MyLocation(String name) {
        this.locationName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return locationName;
    }

    public void setLetterFrom(Letter letter) {
        lettersFrom.add(letter);
    }

    public void setLetterTo(Letter letter) {
        lettersTo.add(letter);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        this.locationName = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
