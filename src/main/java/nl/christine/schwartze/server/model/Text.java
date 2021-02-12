/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "text")
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("text_string")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String textString;

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
}
