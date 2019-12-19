/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.MyLocation;

public class CombineLocationResult {

    @JsonProperty("location1")
    private MyLocation location1;

    @JsonProperty("location2")
    private MyLocation location2;

    public void setLocation1(MyLocation location) {
        this.location1 = location;
    }

    public void setLocation2(MyLocation location) {
        this.location2 = location;
    }

    public MyLocation getLocation1(){
        return location1;
    }

    public MyLocation getLocation2(){
        return location2;
    }
}
