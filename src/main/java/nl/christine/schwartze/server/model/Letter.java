/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.apache.log4j.Logger;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "letters")
public class Letter {

    public static final String TEXT = "text";
    public static final String COLLECTION = "collectie";
    @Transient
    Logger logger = Logger.getLogger(Letter.class);

    public static final String NUMBER = "number";
    public static final String DATE = "date";
    public static final String SENDER = "senders";
    public static final String RECIPIENT = "recipients";
    public static final String FROM_LOCATION = "sender_location";
    public static final String TO_LOCATION = "recipient_location";
    public static final String REMARKS = "remarks";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = NUMBER)
    @JsonProperty(NUMBER)
    private int number;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "letter_sender",
            joinColumns = @JoinColumn(name = "letter_id"),
            inverseJoinColumns = @JoinColumn(name = "sender_id")
    )
    @JsonProperty(SENDER)
    private List<Person> senders = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "letter_recipient",
            joinColumns = @JoinColumn(name = "letter_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    @JsonProperty(RECIPIENT)
    private List<Person> recipients = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "letter_fromlocation",
            joinColumns = @JoinColumn(name = "letter_id"),
            inverseJoinColumns = @JoinColumn(name = "fromlocation_id")
    )
    @JsonProperty(FROM_LOCATION)
    private List<MyLocation> fromLocations = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "letter_tolocation",
            joinColumns = @JoinColumn(name = "letter_id"),
            inverseJoinColumns = @JoinColumn(name = "tolocation_id")
    )
    @JsonProperty(TO_LOCATION)
    private List<MyLocation> toLocations = new ArrayList<>();

    @Column(name = DATE)
    @JsonProperty(DATE)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Column(name = "comment")
    @JsonProperty(REMARKS)
    private String remarks;

    @OneToOne
    @JsonProperty(TEXT)
    private Text text;

    @JsonProperty(COLLECTION)
    @ManyToOne(cascade = CascadeType.ALL)
    private Collectie collectie;

    public Letter() {
    }

    public int getId() {
        return id;
    }

    public Letter(int number) {
        this.number = number;
    }

    public void setSender(Person fromPerson) {
        senders.add(fromPerson);
    }

    public void setRecipient(Person toPerson) {
        recipients.add(toPerson);
    }

    public void addFromLocation(MyLocation fromLocation) {
        fromLocations.add(fromLocation);
    }

    public void removeFromLocation(MyLocation fromLocation) {
        fromLocations.remove(fromLocation);
    }

    public void addToLocation(MyLocation toLocation) {
        toLocations.add(toLocation);
    }

    public void removeToLocation(MyLocation toLocation) {
        toLocations.remove(toLocation);
    }

    public int getNumber() {
        return number;
    }

    public List<Person> getSenders() {
        return senders;
    }

    public List<Person> getRecipients() {
        return recipients;
    }

    public void setComment(String comment) {
        this.remarks = comment;
    }

    public String toString() {
        return number + " " + date + " " +
                ((senders == null || senders.isEmpty()) ? "none" : senders.get(0).getFirstName()) + " " +
                ((recipients == null || recipients.isEmpty()) ? "none" : recipients.get(0).getFirstName()) + " " +
                ((fromLocations == null || fromLocations.isEmpty()) ? "none" : fromLocations.get(0).getName()) + " " +
                ((toLocations == null || toLocations.isEmpty()) ? "none" : toLocations.get(0).getName());
    }

    public void setDate(String dateString) {
        if (dateString != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                this.date = LocalDate.parse(dateString, formatter);
            } catch (Exception e) {
                logger.error("Error setting date", e);
            }
        }
    }

    public String getComment() {
        return remarks;
    }

    public void setNumber(int letterNumber) {
        this.number = letterNumber;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setLocalDate(LocalDate date){
        this.date = date;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MyLocation> getFromLocations(){
        return fromLocations;
    }
    public List<MyLocation> getToLocations(){
        return toLocations;
    }

    public Collectie getCollectie() {
        return collectie;
    }

    public void setCollectie(Collectie collectie) {
        this.collectie = collectie;
    }
}
