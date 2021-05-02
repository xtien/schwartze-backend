/*
 * Copyright (c) 2018 - 2021, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.image.impl;

import nl.christine.schwartze.server.image.ImageService;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * User: christine
 * Date: 12/28/18 6:18 PM
 */
@Component("imageService")
public class ImageServiceImpl implements ImageService {

    private String imagesDirectory;
    private String cacheDir;
    ExecutorService es = Executors.newCachedThreadPool();

    @Autowired
    private SchwartzeProperties properties;

    private Logger logger = Logger.getLogger(ImageServiceImpl.class);

    @PostConstruct
    public void init() {
        imagesDirectory = properties.getProperty("images_directory");
        cacheDir = properties.getProperty("cache_directory");
        File cache = new File(cacheDir);
        if(!cache.exists()){
            cache.mkdir();
        }
    }

    @Override
    public List<String> getImages(int letterNumber) {

        File dir = new File(imagesDirectory + "/" + letterNumber + "/");
        if (dir.exists()) {
            return Arrays.stream(dir.listFiles())
                    .sorted(Comparator.comparing(File::getName))
                    .filter((file -> file.getName().toLowerCase().endsWith(".jpg")))
                    .map(file -> {
                        if (existsInCache(letterNumber, file.getName())) {
                            try {
                                return getFromCache(letterNumber, file.getName());
                            } catch (IOException e) {
                                logger.error("error reading cached file", e);
                                return null;
                            }
                        } else {
                            try {
                                return readFileAndResize(letterNumber, file);
                            } catch (IOException e) {
                                logger.error("error reading image file", e);
                                return null;
                            }
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private String readFileAndResize(int letterNumber, File file) throws IOException {

        byte[] fileData;
        BufferedImage bImage = ImageIO.read(file);
        int ratio = 5;
        BufferedImage newImage = resizeImage(bImage, bImage.getWidth() / ratio, bImage.getHeight() / ratio, bImage.getType());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "jpg", baos);
        baos.flush();
        fileData = baos.toByteArray();
        baos.close();

        writeToCache(letterNumber, file.getName(), fileData);

        return Base64.encodeBase64String(fileData);
    }

    private String getFromCache(int letterNumber, String fileName) throws IOException {

        File file = new File(cacheDir + "/" + letterNumber + "/" + fileName);
        byte[] fileData = new byte[(int) file.length()];

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            dis.readFully(fileData);
        }
        return Base64.encodeBase64String(fileData);
    }

    private boolean existsInCache(int letterNumber, String fileName) {
        File dir = new File(cacheDir + "/" + letterNumber + "/" + fileName);
        return dir.exists();
    }

    private void writeToCache(int letterNumber, String fileName, byte[] fileData) throws IOException {

        es.execute(() -> {
            File dir = new File(cacheDir + "/" + letterNumber);
            if(!dir.exists()){
                dir.mkdir();
            }

            try (OutputStream os = new FileOutputStream(new File(cacheDir + "/" + letterNumber + "/" + fileName));){
                os.write(fileData);
            } catch (FileNotFoundException e) {
                logger.error("cache writing error", e);
            } catch (IOException e) {
                logger.error("cache writing error", e);
            }

        });
     }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
