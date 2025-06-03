/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import java.util.List;

public class LocationsRequest {

    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setId(List<Integer> ids) {
        this.ids = ids;
    }
}
