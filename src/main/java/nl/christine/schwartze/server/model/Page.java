/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pages")
public class Page {

    public static final String ID = "id";
    private static final String REFERENCES = "references";
    private static final String PAGE_NUMBER = "page_number";
    private static final String CHAPTER_NUMBER = "chapter_number";
    private static final String PICTURE_URL = "picture_url";
    private static final String PICTURE_CAPTION = "picture_caption";
    private static final String CHAPTER_TITLE = "chapter_title";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(ID)
    private int id;

    @Column(name = CHAPTER_NUMBER)
    @JsonProperty(CHAPTER_NUMBER)
    private String chapterNumber;

    @Column(name = PICTURE_URL)
    @JsonProperty(PICTURE_URL)
    private String pictureUrl;

    @Column(name = PICTURE_CAPTION)
    @JsonProperty(PICTURE_CAPTION)
    private String pictureCaption;

    @Column(name = PAGE_NUMBER)
    @JsonProperty(PAGE_NUMBER)
    private String pageNumber;

    @Transient
    @JsonProperty(CHAPTER_TITLE)
    private String chapterTitle;

    @Column(name = REFERENCES)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(REFERENCES)
    private List<PageReference> references = new ArrayList<>();

    public List<PageReference> getReferences() {
        return references;
    }

    public void addReference(PageReference reference) {
        this.references.add(reference);
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getPictureCaption() {
        return pictureCaption;
    }

    public void setPictureCaption(String pictureCaption) {
        this.pictureCaption = pictureCaption;
    }
}
