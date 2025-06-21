/*
 * Copyright (c) 2025, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.test.service;

import com.deepl.api.DeepLException;
import nl.christine.schwartze.server.service.FileService;
import nl.christine.schwartze.server.service.LetterService;
import nl.christine.schwartze.server.service.TextService;
import nl.christine.schwartze.server.service.TranslateService;
import nl.christine.schwartze.server.service.result.LetterTextResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/testApplicationContext.xml"})

public class TranslateTest {

    private String language = "es";
    private int letterNumber = 17;
    private LetterTextResult letterTextResult;
    String testText = "Brief in het Nederlands";
    String translatedText = "Carta en neerlandés";

    @Value("${defaultlanguage}")
    private String defaultLanguage;

    String fileName1 = "/home/christine/Documents/Schwartze/nl/17/tekst.txt";
    String fileName2 = "/home/christine/Documents/Schwartze/es/17/tekst.txt";

    @MockitoBean
    private TextService textService;

    @Autowired
    private TranslateService translateService;

    @MockitoBean
    private FileService fileService;

    @MockitoBean
    LetterService letterService;

    @Test
    public void testTranslate() throws DeepLException, IOException, InterruptedException {

        letterTextResult = new LetterTextResult();
        letterTextResult.setText(testText);

        when(textService.getLetterText(letterNumber, defaultLanguage)).thenReturn(letterTextResult);
        when(fileService.readFile(fileName1)).thenReturn(testText);

        String result = translateService.translateLetter(letterNumber, language, testText);
        assert result.equals(translatedText);
    }

}
