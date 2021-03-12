/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.ContentItem;

import java.util.List;

public class ContentResult {

    @JsonProperty("content")
    private List<ContentItem> content;

    public void setContent(List<ContentItem> list) {
        this.content = list;
    }
}
