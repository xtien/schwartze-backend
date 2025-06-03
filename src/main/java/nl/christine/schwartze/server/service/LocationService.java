/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.controller.request.EditLinkRequest;
import nl.christine.schwartze.server.controller.result.CombineLocationResult;
import nl.christine.schwartze.server.controller.result.EditLinkResult;
import nl.christine.schwartze.server.exception.LocationNotFoundException;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Text;

import java.util.List;

/**
 * User: christine
 * Date: 12/29/18 12:26 PM
 */
public interface LocationService {

    MyLocation updateLocationComment(MyLocation location);

    MyLocation getLocation(int id);

    MyLocation addLocation(MyLocation location);

    void deleteLocation(int id) throws LocationNotFoundException;

    Text getText(int id);

    EditLinkResult editLink(EditLinkRequest request);

    List<MyLocation> getAllLocations();

    CombineLocationResult getCombineLocations(int id1, int id2);

    CombineLocationResult putCombineLocations(int id1, int id2);

    MyLocation updateLocation(int id, String name, String comment);

    List<MyLocation> getLocations(List<Integer> ids);
}
