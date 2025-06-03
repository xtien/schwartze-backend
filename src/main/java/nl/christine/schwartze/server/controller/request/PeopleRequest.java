/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.request;

import nl.christine.schwartze.server.controller.enums.PersonOrderEnum;

import java.util.ArrayList;
import java.util.List;

public class PeopleRequest {

    private int startNumber;
    private int totalNumber;
    private List<Integer> ids = new ArrayList<>();
    private PersonOrderEnum orderBy;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids.addAll(ids);
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public PersonOrderEnum getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(PersonOrderEnum orderBy) {
        this.orderBy = orderBy;
    }
}
