/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "page_references")
public class PageReference {

    public static final String ID = "id";
    private static final String TYPE = "type";
    private static final String KEY = "key";
    private static final String DESCRIPTION = "description";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(ID)
    private int id;

    @JsonIgnore
    @OneToOne
    private Page page;

    @Column(name = TYPE)
    @JsonProperty(TYPE)
    private ReferenceType type;

    @Column(name = KEY)
    @JsonProperty(KEY)
    private String key;

    @Column(name = DESCRIPTION)
    @JsonProperty(DESCRIPTION)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(ReferenceType type) {
        this.type = type;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getId(){
        return id;
    }
}
