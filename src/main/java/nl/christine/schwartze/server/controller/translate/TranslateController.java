/*
 * Copyright (c) 2025, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.controller.translate;

import com.deepl.api.DeepLException;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.christine.schwartze.server.controller.request.TranslateRequest;
import nl.christine.schwartze.server.controller.result.TranslateResult;
import nl.christine.schwartze.server.service.TextService;
import nl.christine.schwartze.server.service.TranslateService;
import nl.christine.schwartze.server.service.result.LetterTextResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/translate")
@Tag(name = "Admin Translate", description = "")
public class TranslateController {

    @Autowired
    TranslateService translateService;

    @Autowired
    TextService textService;

    @PostMapping(value = "/translateLetter/")
    public TranslateResult translateLetter(@RequestBody TranslateRequest request) throws IOException, DeepLException, InterruptedException {
        TranslateResult result = new TranslateResult();
        LetterTextResult l = textService.getLetterText(request.getNumber(), request.getLanguage());
        String newText = translateService.translateLetter(request.getNumber(), request.getLanguage(), l.getText());
        result.setText(newText);
        result.setLanguage(request.getLanguage());
        return result;
    }
}
