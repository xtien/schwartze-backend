/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {

    public static final String NAME = "first_name";
    public static final String FULL_NAME = "middle_name";
    public static final String TUSSENVOEGSEL = "tussenvoegsel";
    public static final String LAST_NAME = "last_name";
    public static final String COMMENT = "comment";
    public static final String LINKS = "links";
    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String IMAGE_URL = "image_url";
    public static final String IMAGE_CAPTION = "image_caption";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String DATE_OF_DEATH = "date_of_death";
    public static final String HAS_LETTERS_FROM = "brieven_van";
    public static final String HAS_LETTERS_TO = "brieven_aan";

    @Id
    @JsonProperty(ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = NAME)
    @JsonProperty("nick_name")
    private String name;

    @Column(name = FULL_NAME)
    @JsonProperty("full_name")
    private String fullName;

    @Column(name = TUSSENVOEGSEL)
    @JsonProperty(TUSSENVOEGSEL)
    private String tussenvoegsel;

    @Column(name = LAST_NAME)
    @JsonProperty(LAST_NAME)
    private String lastName;

    @Column(name = DATE_OF_BIRTH)
    @JsonProperty(DATE_OF_BIRTH)
    private String dateOfBirth;

    @Column(name = DATE_OF_DEATH)
    @JsonProperty(DATE_OF_DEATH)
    private String dateOfDeath;

    @Column(name = COMMENT)
    @JsonProperty(COMMENT)
    private String comment;

    @Column(name=IMAGE_URL)
    @JsonProperty(IMAGE_URL)
    private String imageUrl;

    @Column(name=IMAGE_CAPTION)
    @JsonProperty(IMAGE_CAPTION)
    private String imageCaption;

    @JsonIgnore
    @ManyToMany(mappedBy = "senders", cascade = CascadeType.ALL)
    private List<Letter> lettersWritten = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "recipients", cascade = CascadeType.ALL)
    private List<Letter> lettersReceived = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "person")
    @JsonProperty(LINKS)
    private List<Link> links = new ArrayList<>();

    @OneToOne
    @JsonProperty(TEXT)
    private Text text;

    @JsonProperty(HAS_LETTERS_FROM)
    @Transient
    private Boolean hasLettersFrom;

    @JsonProperty(HAS_LETTERS_TO)
    @Transient
    private Boolean hasLettersTo;

    public Person() {
        // used for deserialization
    }

    public void setName(String s) {
        name = s;
    }

    public void setFullName(String s) {
        fullName = s;
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

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
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
        if (name == null) {
            name = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (fullName == null) {
            fullName = "";
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Letter> getLettersWritten() {
        return lettersWritten;
    }

    public List<Letter> getLettersReceived() {
        return lettersReceived;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text){
        this.text = text;
    }

    public List<Link> getLinks() {
        return links;
    }

    @JsonIgnore
    public void addLinks(List<Link> links) {
        this.links.addAll(links);
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
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
        Person rhs = (Person) obj;
        return new EqualsBuilder()
                .append(id, rhs.id)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(id).
                toHashCode();
    }

    public String toString(){
        return id + " " + name + " " + lastName + " " + dateOfBirth;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public Boolean getHasLettersFrom() {
        return hasLettersFrom;
    }

    public void setHasLettersFrom(Boolean hasLettersFrom) {
        this.hasLettersFrom = hasLettersFrom;
    }

    public Boolean getHasLettersTo() {
        return hasLettersTo;
    }

    public void setHasLettersTo(Boolean hasLettersTo) {
        this.hasLettersTo = hasLettersTo;
    }
}
