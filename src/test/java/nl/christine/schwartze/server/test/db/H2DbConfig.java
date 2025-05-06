/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@Profile("test")
@PropertySource("application-test.properties")
public class H2DbConfig {

    @Value("${spring.inmemory-ds.url}")
    private String url;

    @Value("${spring.inmemory-ds.username}")
    private String userName;

    @Value("${spring.inmemory-ds.password}")
    private String password;

    @Value("${spring.inmemory-ds.driver-class-name}")
    private String driverClass;

    @Bean(name = "datasource")
    public DataSource inmemoryDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(url)
                .username(userName)
                .password(password)
                .build();
    }
}
