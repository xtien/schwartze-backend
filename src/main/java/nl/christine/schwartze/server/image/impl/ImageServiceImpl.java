/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.image.impl;

import nl.christine.schwartze.server.image.ImageService;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: christine
 * Date: 12/28/18 6:18 PM
 */
@Component("imageService")
public class ImageServiceImpl implements ImageService {

    private String imagesDirectory;
    private String imagesUrl;

    @Autowired
    private SchwartzeProperties properties;

    private Logger logger = Logger.getLogger(ImageServiceImpl.class);

    @PostConstruct
    public void init() {
        imagesDirectory = properties.getProperty("images_directory");
        imagesUrl = properties.getProperty(("images_url"));
    }

    @Override
    public List<String> getImages(int letterNumber) {

        return Arrays.stream(new File(imagesDirectory + "/" + letterNumber + "/").listFiles())
                .sorted(Comparator.comparing(File::getName))
                .filter((file -> file.getName().toLowerCase().endsWith(".jpg")))
                .map(file -> imagesUrl + letterNumber + File.separator + file.getName())
                .collect(Collectors.toList());
    }

    private String createBlob(File imageFile) {

        String resultBlob = "";

        try {
            resultBlob = new String(Base64.encodeBase64(IOUtils.toByteArray(new FileInputStream(imageFile))), "UTF-8");
        } catch (FileNotFoundException fnfe) {
            logger.error("createBlob fileNotFound", fnfe);
        } catch (IOException ioe) {
            logger.error("createBlob IO Exception", ioe);
        }

        return resultBlob;
    }
}
