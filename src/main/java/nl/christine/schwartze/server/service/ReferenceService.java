/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.controller.request.EditReferenceLinkRequest;
import nl.christine.schwartze.server.controller.request.RemoveReferenceLinkRequest;
import nl.christine.schwartze.server.model.References;

public interface ReferenceService {

    References getReferences(String type);

    References updateReferences(References references);

    void removeReferenceLink(RemoveReferenceLinkRequest request);

    References editLink(EditReferenceLinkRequest request);
}
