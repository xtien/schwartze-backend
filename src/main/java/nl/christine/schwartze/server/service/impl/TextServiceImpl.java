/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.service.impl;

import com.deepl.api.DeepLException;
import nl.christine.schwartze.server.controller.request.TextRequest;
import nl.christine.schwartze.server.dao.*;
import nl.christine.schwartze.server.model.*;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.FileService;
import nl.christine.schwartze.server.service.TextService;
import nl.christine.schwartze.server.service.TranslateService;
import nl.christine.schwartze.server.service.result.LetterTextResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component("textService")
public class TextServiceImpl implements TextService {

    Logger logger = LoggerFactory.getLogger(TextServiceImpl.class);

    private String lettersDirectory;
    private String textDocumentName;

    @Autowired
    private TranslateService translateService;

    @Autowired
    private FileService fileService;

    @Autowired
    private TextDao textDao;

    @Autowired
    private PersonDao personDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SchwartzeProperties properties;

    @Value("${defaultlanguage}")
    private String defaultLanguage;

    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
        textDocumentName = properties.getProperty("text_document_name");
    }


    @Override
    @Transactional("transactionManager")
    public Text updateText(TextRequest request) {

        if (request.getId() != null) {
            return textDao.updateText(request.getText());
        } else if (request.getPersonId() != null) {
            Person person = personDao.getPerson(request.getPersonId());
            if (person.getText() == null) {
                person.setText(new Text());
                textDao.persist(person.getText());
            }
            if (request.getText() != null) {
                person.getText().setTextString(request.getText().getTextString());
            } else if (StringUtils.isNotEmpty(request.getTextString())) {
                person.getText().setTextString(request.getTextString());
            }
            return person.getText();
        } else if (request.getLocationId() != null) {
            MyLocation location = locationDao.getLocation(request.getLocationId());
            if (location.getText() == null) {
                location.setText(new Text());
                textDao.persist(location.getText());
            }
            if (request.getText() != null) {
                location.getText().setTextString(request.getText().getTextString());
            } else if (StringUtils.isNotEmpty(request.getTextString())) {
                location.getText().setTextString(request.getTextString());
            }
            return location.getText();
        } else if (request.getLetterId() != null) {
            Letter letter = letterDao.getLetterForId(request.getLetterId());
            if (letter.getText() == null) {
                letter.setText(new Text());
                textDao.persist(letter.getText());
            }
            if (request.getText() != null) {
                letter.getText().setTextString(request.getText().getTextString());
            } else if (StringUtils.isNotEmpty(request.getTextString())) {
                letter.getText().setTextString(request.getTextString());
            }
            return letter.getText();
        } else if (request.getSubjectId() != null) {
            Subject subject = subjectDao.getSubjectById(request.getSubjectId());
            if (!subject.getTexts().containsKey(request.getLanguage())) {
                subject.getTexts().put(request.getLanguage(), new Text());
                subject.getTexts().get(request.getLanguage()).setLanguage(request.getLanguage());
                subjectDao.persist(subject.getTexts().get(request.getLanguage()));
            }
            if (request.getText() != null) {
                subject.getTexts().get(request.getLanguage()).setTextString(request.getText().getTextString());
            } else if (StringUtils.isNotEmpty(request.getTextString())) {
                subject.getTexts().get(request.getLanguage()).setTextString(request.getTextString());
            }
            return subject.getTexts().get(request.getLanguage());
        }

        return null;
    }

    @Override
    @Transactional("transactionManager")
    public Text getText(Integer id) {
        return textDao.getText(id);
    }

    @Override
    public LetterTextResult getLetterText(int letterNumber, String language) throws IOException, DeepLException, InterruptedException {
        String result = "";
        String defaultFileName = lettersDirectory + File.separator + letterNumber + File.separator + textDocumentName;

        if (language == null || language.isEmpty() || language.equals(defaultLanguage)) {
            result = fileService.readFile(defaultFileName);
        } else {
            String langDir = File.separator + language + File.separator;
            String fileName = lettersDirectory + langDir + letterNumber + File.separator + textDocumentName;
            if (!fileService.existsFile(fileName)) {
                String t = fileService.readFile(defaultFileName);
                result = translateService.translateLetter(letterNumber, t, language);
                writeLetterText(letterNumber, language, result);
            } else {
                result = fileService.readFile(fileName);
            }
        }

        LetterTextResult letterTextResult = new LetterTextResult();
        letterTextResult.setText(result);
        return letterTextResult;
    }

    @Override
    public void writeLetterText(int letterNumber, String language, String text) throws IOException {
        if (language == null || language.isEmpty()) {
            return;
        }
        String langDir = lettersDirectory + (File.separator + language + File.separator);
        if (!new File(langDir).exists()) {
            new File(langDir).createNewFile();
        }

        String fileName = langDir + letterNumber + File.separator + "tekst.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(text.getBytes(StandardCharsets.UTF_8));
        }
    }

}
