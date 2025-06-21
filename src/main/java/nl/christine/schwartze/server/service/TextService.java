/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service;

import com.deepl.api.DeepLException;
import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.model.Text;
import nl.christine.schwartze.server.service.result.LetterTextResult;

import java.io.IOException;

public interface TextService {

    Text updateText(TextRequest request);

    Text getText(Integer id);

    LetterTextResult getLetterText(int letterNumber, String language) throws IOException, DeepLException, InterruptedException;

    void writeLetterText(int letterNumber, String language, String text) throws IOException;
}
