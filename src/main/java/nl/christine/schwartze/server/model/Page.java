/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pages")
public class Page {

    public static final String ID = "id";
    private static final String REFERENCES = "references";
    private static final String PAGE_NUMBER = "page_number";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(ID)
    private int id;

    @Column(name=PAGE_NUMBER)
    @JsonProperty(PAGE_NUMBER)
    private String pageNumber;

    @Column(name = REFERENCES)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(REFERENCES)
    private List<PageReference> references;

    public List<PageReference> getReferences() {
        return references;
    }

    public void addReference(PageReference reference) {
        this.references.add(reference);
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }
}
