/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.modelimport;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "letters")
public class ImportLetter {
    public static final String NUMBER = "number";
    public static final String DATE = "date";
    public static final String SENDER = "sender";
    public static final String RECIPIENT = "recipient";
    public static final String FROM_LOCATION = "sender_location";
    public static final String TO_LOCATION = "recipient_location";
    public static final String COMMENT = "comment";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = NUMBER)
    @JsonProperty(NUMBER)
    private int number;

    @Column(name = SENDER)
    @JsonProperty(SENDER)
    private String sender;

    @Column(name = RECIPIENT)
    @JsonProperty(RECIPIENT)
    private String recipient;

    @Column(name = FROM_LOCATION)
    @JsonProperty(FROM_LOCATION)
    private String fromLocation;

    @Column(name = TO_LOCATION)
    @JsonProperty(TO_LOCATION)
    private String toLocation;

    @Column(name = DATE)
    @JsonProperty(DATE)
    private String date;

    @Column(name = COMMENT)
    @JsonProperty(COMMENT)
    private String comment;

    public ImportLetter() {
        // used for deserialization
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public boolean hasSender() {
        return sender != null;
    }

    public boolean hasRecipient() {
        return recipient != null;
    }

    public boolean hasFromLocation() {
        return fromLocation != null;
    }

    public boolean hasToLocation() {
        return toLocation != null;
    }

    public int getNumber() {
        return number;
    }

    public String getComment() {
        return comment;
    }

    public String toString(){
        return number + " " + date + " " + sender + " to " + recipient;
    }

    public String getDate() {
        return date;
    }
}
