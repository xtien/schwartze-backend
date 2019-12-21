/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.request.RemoveReferenceLinkRequest;
import nl.christine.schwartze.server.dao.LinkDao;
import nl.christine.schwartze.server.dao.ReferenceDao;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.References;
import nl.christine.schwartze.server.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("referenceService")
public class ReferenceServiceImpl implements ReferenceService {

    @Autowired
    private ReferenceDao referenceDao;

    @Autowired
    private LinkDao linkDao;

    @Override
    public References getReferences(String type) {

        return referenceDao.getReferences(type);
    }

    @Override
    @Transactional("transactionManager")
    public References updateReferences(References references) {
        return referenceDao.updateReferences(references);
    }

    @Override
    @Transactional("transactionManager")
    public References removeReferenceLink(RemoveReferenceLinkRequest request) {
        References existingReferences = referenceDao.getReferences(request.getType());
        if (existingReferences != null) {
            Link link = existingReferences.getLinks().stream().filter(x -> x.getId() == request.getLinkId()).findFirst().get();
            existingReferences.getLinks().removeIf(x -> x.getId() == request.getLinkId());
            linkDao.remove(link);
        }
        return existingReferences;
    }
}
