/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.result.AddLetterResult;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class AddLetterController  {

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/add_letter/")
    public AddLetterResult addLetter(ImportLetter letter) {
        throw new IllegalStateException("not implemented");
    }
}
