/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
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
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@Profile("!test")
public class SchwartzeDbConfig {

    @Value("${spring.datasource.url}")
    private String url;

    private String userName;

    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Autowired
    public SchwartzeDbConfig(SchwartzeProperties properties) {
        password = properties.getProperty("dbpassword");
        userName = properties.getProperty("dbusername");
    }

    @Bean(name = "datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(url)
                .username(userName)
                .password(password)
                .build();
    }
}
