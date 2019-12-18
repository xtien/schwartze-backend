/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User: christine
 * Date: 12/16/18 10:06 PM
 */
@SpringBootApplication
public class Application {

    public static final String UI_HOST = "https://pengo.christine.nl";
  //  public static final String UI_HOST = "http://www.schwartze-ansingh.nl:3000";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
