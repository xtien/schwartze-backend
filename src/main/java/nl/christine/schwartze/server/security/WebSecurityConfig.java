/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("!test")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    MySuccessHandler mySuccessHandler;

    @Autowired
    MyFailureHandler myFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("WRITE_PRIVILEGE")
                .antMatchers("/user/**").hasAuthority("WRITE_PRIVILEGE")
                .antMatchers("/v2/api-docs",
                        "/swagger-ui.html", "/swagger-ui").hasAuthority("WRITE_PRIVILEGE")
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll()
        ;
    }
}
