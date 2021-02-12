/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;

/**
 * User: christine
 * Date: 12/28/18 6:08 PM
 */
public class ImagesResult extends ApiResult {

    @JsonProperty("images")
    private List<String> images;

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Collection<String> getImages() {
        return images;
    }

}
