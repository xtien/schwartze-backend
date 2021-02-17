/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Page;

public class PageTextResult {

    @JsonProperty("text")
    private String text;

    @JsonProperty("error_text")
    private String errorText;

    @JsonProperty("page")
    private Page page;

    @JsonProperty("language")
    private String language;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setErrorText(String text) {
        this.errorText = text;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
