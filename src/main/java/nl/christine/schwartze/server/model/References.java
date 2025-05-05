/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lit_refs")
public class References {

    public static final String LINKS = "links";
    public static final String ID = "id";
    public static final String REFTYPE = "reftype";
    public static final String TYPE = "type";

    @Id
    @JsonProperty(ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = REFTYPE)
    @JsonProperty(TYPE)
    private String type;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonProperty(LINKS)
    private List<Link> links = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void addLink(Link link) {
        if(this.links == null){
            this.links = new ArrayList<>();
        }
        this.links.add(link);
    }
}
