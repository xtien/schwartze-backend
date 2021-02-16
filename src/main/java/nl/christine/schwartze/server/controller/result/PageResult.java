/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Page;

public class PageResult {

    @JsonProperty("page")
    private Page page;

    @JsonProperty("text")
    private String text;

    @JsonProperty("pageId")
    private String pageId;

    @JsonProperty("chapterId")
    private String chapterId;

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
