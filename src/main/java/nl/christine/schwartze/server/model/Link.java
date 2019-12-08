/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("link_name")
    @Column(name = "linkname")
    private String linkName;

    @JsonProperty("link_url")
    @Column(name = "linkUrl")
    private String linkUrl;

    public Link() {
    }

    public Link(String linkName, String linkUrl) {
        this.linkName = linkName;
        this.linkUrl = linkUrl;
    }

    public int getId() {
        return id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
