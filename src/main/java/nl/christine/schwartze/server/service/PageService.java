/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.model.Page;
import nl.christine.schwartze.server.model.PageReference;

public interface PageService {

    Page getPage(String pageNumber, String chapterNumber);

    Page addPage(String pageNumber, String chapterNumber);

    Page updatePage(Page page);

    void removePage(String pageNumber, String chapterNumber);

    Page addPageReference(String pageNumber, String chapterNumber, PageReference reference);

    Page removePageReference(String pageNumber, String chapterNumber, PageReference reference);
}
