/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Text;

public class SubjectRequest {

    @JsonProperty("subject_id")
    private Integer subjectId;

    @JsonProperty("subject_name")
    private String subjectName;

    @JsonProperty("subject_text")
    private Text subjectText;

    @JsonProperty("language")
    private String language;

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Text getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(Text subjectText) {
        this.subjectText = subjectText;
    }
}
