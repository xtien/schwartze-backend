/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.References;

public class UpdateReferencesResult {

    @JsonProperty("references")
    private References references;

    public References getReferences() {
        return references;
    }

    public void setReferences(References references) {
        this.references = references;
    }
}
