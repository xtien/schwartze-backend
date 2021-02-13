/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.PageReference;

public class PageReferenceRequest {

    @JsonProperty("page_number")
    private String pageNumber;

    @JsonProperty("chapter_number")
    private String chapterNumber;

    @JsonProperty("reference")
    private PageReference reference;

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PageReference getReference() {
        return reference;
    }

    public void setReference(PageReference reference) {
        this.reference = reference;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }
}
