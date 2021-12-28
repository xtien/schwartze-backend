/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.controller.request.EditReferenceLinkRequest;
import nl.christine.schwartze.server.controller.request.RemoveReferenceLinkRequest;
import nl.christine.schwartze.server.dao.LinkDao;
import nl.christine.schwartze.server.dao.ReferenceDao;
import nl.christine.schwartze.server.model.Link;
import nl.christine.schwartze.server.model.References;
import nl.christine.schwartze.server.service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
            Optional<Link> l = existingReferences.getLinks().stream().filter(x -> x.getId() == request.getLinkId()).findFirst();
            Link link = null;
            if (l.isPresent()) {
                link = l.get();
            }
            existingReferences.getLinks().removeIf(x -> x.getId() == request.getLinkId());
            linkDao.remove(link);
        }
        return existingReferences;
    }

    @Override
    @Transactional("transactionManager")
    public References editLink(EditReferenceLinkRequest request) {
        References references = referenceDao.getReferences(request.getType());
        Link link = references.getLinks().stream().filter((x -> x.getId() == request.getLinkId())).findFirst().orElse(null);
        if (link == null) {
            link = new Link();
            linkDao.persist(link);
            references.getLinks().add(link);
        }
        link.setLinkName(request.getLinkName());
        link.setLinkUrl(request.getLinkUrl());

        return references;
    }
}
