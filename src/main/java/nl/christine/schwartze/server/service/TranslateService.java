/*
 * Copyright (c) 2025, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import com.deepl.api.DeepLException;

import java.io.IOException;

public interface TranslateService {

    public String translateLetter(int letterNumber,String text, String language) throws IOException, DeepLException, InterruptedException;
}
