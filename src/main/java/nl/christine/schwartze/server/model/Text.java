/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;

import java.sql.Types;

@Entity
@Table(name = "text")
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("text_title")
    private String textTitle;

    @JsonProperty("text_string")
    @Lob
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String textString;

    @JsonProperty("language")
    private String language;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String text) {
        this.textString = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
