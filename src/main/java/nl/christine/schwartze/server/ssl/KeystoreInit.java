/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.ssl;

import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * https://stackoverflow.com/questions/44969873/spring-boot-key-store-password-set-in-code
 */
@Component
public class KeystoreInit {

    private final String keystoreAlias;
    private final String keystoreType;
    private final String keystorePassword;
    private final String keystoreLocation;
    private final String keyPassword;

    @Autowired
    public KeystoreInit(SchwartzeProperties properties) {
        keystorePassword = properties.getProperty("keystorePassword");
        keystoreLocation = properties.getProperty("keystoreLocation");
        keystoreAlias = properties.getProperty(("keyAlias"));
        keyPassword = properties.getProperty("keyPassword");
        keystoreType = properties.getProperty(("keystoreType"));
    }

    @Bean
    @DependsOn("schwartzeProperties")
    public ServerProperties serverProperties() {
        final ServerProperties serverProperties = new ServerProperties();
        final Ssl ssl = new Ssl();
        ssl.setKeyPassword(keystorePassword);
        System.setProperty("server.ssl.key-store-password", keystorePassword);
        System.setProperty("server.ssl.key-store", keystoreLocation);
        System.setProperty("server.ssl.key-alias", keystoreAlias);
        System.setProperty("server.ssl.key-store-type", keystoreType);
        //      System.setProperty("server.ssl.key-password", keyPassword);
        serverProperties.setSsl(ssl);
        return serverProperties;
    }
}
