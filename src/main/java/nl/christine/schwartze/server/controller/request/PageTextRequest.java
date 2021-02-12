/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageTextRequest {

    @JsonProperty("page")
    private String pageid;

    @JsonProperty("chapter")
    private String chapterId;

    @JsonProperty("language")
    private String language;

    public String getPageId() {
        return pageid;
    }

    public String getChapterId() {
        return chapterId;
    }

    public String getLanguage(){
        return language;
    }
}
