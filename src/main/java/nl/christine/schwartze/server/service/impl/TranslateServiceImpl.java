/*
 * Copyright (c) 2025, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@Component("translateService")
public class TranslateServiceImpl implements TranslateService {

    @Value("${defaultlanguage}")
    private String defaultLanguage;

    @Autowired
    private SchwartzeProperties properties;

    String deeplKey = null;

    @PostConstruct
    public void init() {
        deeplKey = properties.getProperty("deeplKey");  // Replace with your key

    }

    @Override
    public String translateLetter(int letterNumber, String text, String language) throws IOException, DeepLException, InterruptedException {

        String lan = language.equals("en") ? "en-US" : language;
        String translatedLetter = translate(text, lan);
        return translatedLetter;
    }

    @Override
    public String translate(String text, String language) throws DeepLException, InterruptedException {
        String deepLanguage = language.equals("en") ? "en-US" : language;
        Translator translator = new Translator(deeplKey);
        TextResult result = translator.translateText(text, defaultLanguage, deepLanguage);
        return result.getText();
    }

}
