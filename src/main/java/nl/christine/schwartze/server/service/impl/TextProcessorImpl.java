/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.service.TextProcessor;
import org.springframework.stereotype.Component;

//            <p className="navbar-nav"><Link to='/get_letters/0'>Brieven</Link></p>

@Component("textProcessor")
public class TextProcessorImpl implements TextProcessor {

    @Override
    public String process(String text) {
        return text;
    }
}
