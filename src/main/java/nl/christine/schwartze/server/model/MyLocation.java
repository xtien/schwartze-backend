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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class MyLocation {

    public static final String ID = "id";
    public static final String LOCATION_NAME = "location_name";
    public static final String DESCRIPTION = "description";
    public static final String LINKS = "links";
    public static final String COMMENT = "comment";
    private static final String TEXT = "text";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(ID)
    private int id;

    @Column(name = LOCATION_NAME)
    @JsonProperty(LOCATION_NAME)
    private String locationName;

    @Column(name = DESCRIPTION)
    @JsonProperty(DESCRIPTION)
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "fromLocations", cascade = CascadeType.ALL)
    private List<Letter> lettersFrom = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "toLocations", cascade = CascadeType.ALL)
    private List<Letter> lettersTo = new ArrayList<>();

    @Column(name = LINKS)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(LINKS)
    private List<Link> links = new ArrayList<>();

    @Column(name = COMMENT)
    @JsonProperty(COMMENT)
    private String comment;

    @OneToOne
    @JsonProperty(TEXT)
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

    public void addLetterFrom(Letter letter) {
        lettersFrom.add(letter);
    }

    public List<Letter> getLettersFrom() {
        return lettersFrom;
    }

    public void addLetterTo(Letter letter) {
        lettersTo.add(letter);
    }

    public List<Letter> getLettersTo() {
        return lettersTo;
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

    public Text getText() {
        return text;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void addLinks(List<Link> list) {
        this.links.addAll(list);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        MyLocation rhs = (MyLocation) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(id, rhs.id)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(id).
                toHashCode();
    }

    public String toString(){
        return id + " " + locationName + " " +comment;
    }

    public void setLettersFrom(List<Letter> letters) {
        this.lettersFrom = letters;
    }
    public void setLettersTo(List<Letter> letters) {
        this.lettersTo = letters;
    }
}
