/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;

public interface LinkService {

    MyLocation deleteLocationLink(EditLinkRequest request);

    Person deletePersonLink(EditLinkRequest request);
}

