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
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@PropertySource({"application.properties"})
@Profile("!test")
@DependsOn("schwartzeProperties")
public class UserDbConfig {

    @Value("${spring.userdatasource.url}")
    private String url;

   private String userName;

   private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Autowired
    public UserDbConfig(SchwartzeProperties properties){
        password = properties.getProperty("userdbpassword");
        userName = properties.getProperty("userdbusername");
    }

    @Bean(name = "userdatasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(url)
                .username(userName)
                .password(password)
                .build();
    }
 }
