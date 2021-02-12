/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.db;

import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("import")
public class IMportDbConfig {


    @Value("${spring.importdatasource.url}")
    private String importUrl;

    private String userName;
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Autowired
    public IMportDbConfig(SchwartzeProperties properties){
        password = properties.getProperty("importdbpassword");
        userName = properties.getProperty("importdbusername");
    }

    @Bean(name = "importDatasource")
    public DataSource importDatasource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(importUrl)
                .username(userName)
                .password(password)
                .build();
    }
 }
