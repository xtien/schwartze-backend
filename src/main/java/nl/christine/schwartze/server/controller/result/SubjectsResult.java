/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Subject;

import java.util.List;

public class SubjectsResult {

    @JsonProperty("subjects")
    private List<Subject> subjects;
    @JsonProperty("error_text")
    private String errorText;

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText(){
        return errorText;
    }
    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
