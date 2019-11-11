/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "people")
@EnableJpaRepositories(
        basePackages = "nl.christine.schwartze.server.dao",
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "defaultPU")
public class Person {

    public static final String FIRST_NAME = "first_name";
    public static final String MIDDLE_NAME = "middle_name";
    public static final String LAST_NAME = "last_name";
    public static final String COMMENT = "comment";
    public static final String LINKS = "links";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = FIRST_NAME)
    @JsonProperty(FIRST_NAME)
    private String firstName;

    @Column(name = MIDDLE_NAME)
    @JsonProperty(MIDDLE_NAME)
    private String middleName;

    @Column(name = LAST_NAME)
    @JsonProperty(LAST_NAME)
    private String lastName;

    @Column(name = COMMENT)
    @JsonProperty(COMMENT)
    private String comment;

    @JsonIgnore
    @ManyToMany(mappedBy = "senders", cascade = CascadeType.ALL)
    private List<Letter> lettersWritten = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "recipients", cascade = CascadeType.ALL)
    private List<Letter> lettersReceived = new ArrayList<>();

    @Column(name = LINKS)
    private String links;

    public Person() {
        // used for deserialization
    }

    public void setFirstName(String s) {
        firstName = s;
    }

    public void setMiddleName(String s) {
        middleName = s;
    }

    public void setLastName(String substring) {
        lastName = substring;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addLetterWritten(Letter letter) {
        lettersWritten.add(letter);
    }

    public void addLetterReceived(Letter letter) {
        lettersReceived.add(letter);
    }

    public void noNulls() {
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (middleName == null) {
            middleName = "";
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links=links;
    }

    public List<Letter> getLettersWritten(){
        return lettersWritten;
    }

    public List<Letter> getLettersReceived(){
        return lettersReceived;
    }
}
