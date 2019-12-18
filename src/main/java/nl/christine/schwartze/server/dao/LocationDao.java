/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Text;

import java.util.List;

public interface LocationDao {

    List<MyLocation> getLocations();

    MyLocation getLocationByName(MyLocation location);

    MyLocation getLocation(int id);

    MyLocation addLocation(MyLocation location);

    void deleteLocation(int id) throws LocationNotFoundException;

    Text getLocationText(int id);
}
