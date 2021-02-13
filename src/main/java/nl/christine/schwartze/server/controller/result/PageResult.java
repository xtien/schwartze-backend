/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import nl.christine.schwartze.server.model.Page;

public class PageResult {

    private Page page;

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }
}
