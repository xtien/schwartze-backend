/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
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
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
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

        File dir = new File(imagesDirectory + "/" + letterNumber + "/");
        if (dir.exists()) {
            return Arrays.stream(dir.listFiles())
                    .sorted(Comparator.comparing(File::getName))
                    .filter((file -> file.getName().toLowerCase().endsWith(".jpg")))
                    .map(file -> {
                        try {
                            return readFile(file);
                        } catch (IOException e) {
                            logger.error("error reading image file", e);
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private String readFile(File file) throws IOException {

        byte[] fileData;

        BufferedImage bImage = ImageIO.read(file);
        int ratio = 5;

        BufferedImage newImage = resizeImage(bImage, bImage.getWidth() / ratio, bImage.getHeight() / ratio, bImage.getType());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "jpg", baos);
        baos.flush();
        fileData = baos.toByteArray();
        baos.close();
        return Base64.encodeBase64String(fileData);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
