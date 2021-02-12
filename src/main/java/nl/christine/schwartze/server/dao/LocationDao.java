/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Text;

import java.util.List;
import java.util.Optional;

public interface LocationDao {

    List<MyLocation> getLocations();

    MyLocation getLocationByName(MyLocation location);

    MyLocation getLocation(int id);

    MyLocation addNewLocation(MyLocation location);

    void deleteLocation(int id) throws LocationNotFoundException;

    Text getLocationText(int id);

    void merge(MyLocation location);

    void deleteLocation(MyLocation location);

    List<Letter> getLettersForLocation(Optional<Integer> locationId);

    MyLocation saveLocation(MyLocation location);

    void persist(MyLocation location);
}
