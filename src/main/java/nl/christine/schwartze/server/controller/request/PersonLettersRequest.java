/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonLettersRequest {

    @JsonProperty("requestCode")
    private int requestCode = 0;

    @JsonProperty("toFrom")
    private ToFrom  toFrom;

    @JsonProperty("id")
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ToFrom getToFrom() {
        return toFrom;
    }

    public void setToFrom(ToFrom toFrom) {
        this.toFrom = toFrom;
    }
}
