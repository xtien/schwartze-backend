/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "collectie")
public class Collectie {

    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";

    @Id
    private int id;

    @Column(name=NAME)
    @JsonProperty(NAME)
    private String name;

    @Column(name=DESCRIPTION)
    @JsonProperty(DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = "collectie")
    private Collection<Letter> letters = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
