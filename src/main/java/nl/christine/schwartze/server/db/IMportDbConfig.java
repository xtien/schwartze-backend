/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource({"application.properties", "local.properties"})

public class IMportDbConfig {


    @Value("${spring.importdatasource.url}")
    private String importUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;


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
