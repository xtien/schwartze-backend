/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginResult {

    @JsonProperty("authorities")
    private List<String> authorities;

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities.stream().map(x -> x.getAuthority()).collect(Collectors.toList());
    }
}
