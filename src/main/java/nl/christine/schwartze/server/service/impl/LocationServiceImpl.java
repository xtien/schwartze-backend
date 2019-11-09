/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: christine
 * Date: 12/29/18 12:26 PM
 */
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Override
    @Transactional("transactionManager")
    public int updateLocationComment(MyLocation location) {
        int result = -1;
        try {
            MyLocation existingLocation = locationDao.getLocation(location.getId());
            existingLocation.setComment(location.getComment());
        } catch (Exception e) {
            logger.error("Error updating location comment",e);
        }
        return result;
    }

    @Override
    public MyLocation getLocation(int i) {
        return locationDao.getLocation(i);
    }

}
