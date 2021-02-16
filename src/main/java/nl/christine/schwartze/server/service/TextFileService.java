/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;


import nl.christine.schwartze.server.controller.result.PageResult;

public interface TextFileService {

    String getText(String type, String documentName, String language);

    String getPage(String chapterId, String pageId, String language);

    PageResult getNextPage(String chapterId, String pageId, String language);

    PageResult getPreviousPage(String chapterId, String pageId, String language);
}
