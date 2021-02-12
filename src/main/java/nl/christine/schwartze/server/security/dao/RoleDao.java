/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security.dao;

import nl.christine.schwartze.server.security.model.Role;

public interface RoleDao {

    Role findByName(String role);

    void persist(Role role);
}
