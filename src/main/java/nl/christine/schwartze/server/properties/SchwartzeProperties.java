/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.properties;

import nl.christine.schwartze.server.ServerConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;
import java.util.Properties;

@Component("schwartzeProperties")
public class SchwartzeProperties {

    private static final Logger log = Logger.getLogger(SchwartzeProperties.class);

    private Properties properties;
    private String path;
    private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd hh:mm");

    @PostConstruct
    public void init() {
        readProperties();
    }

    private void readProperties() {

        path = "/home/christine" + File.separator + ServerConstants.settings_properties_file;

        File settingsFile = new File(path);

        properties = new Properties();

        if (settingsFile.exists()) {

            try {

                InputStream is = new FileInputStream(settingsFile);

                properties.load(is);

            } catch (IOException e) {
                log.error(e);
            }
        }
        for (Object key : properties.keySet()) {
            log.debug("prop: " + (String) key + " " + properties.getProperty((String) key));
        }
    }

    public boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    public boolean hasProperty(Object key) {
        return containsKey(key);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getIntProperty(String key) {
        String stringProp = getProperty(key);
        int result = -1;
        if (NumberUtils.isCreatable(stringProp)) {
            result = NumberUtils.toInt(stringProp);
        }
        return result;
    }

    public boolean getBooleanProperty(String key) {
        String stringProp = getProperty(key);
        return Boolean.parseBoolean(stringProp);
    }

    public void setBooleanProperty(String key, boolean b) {
        properties.setProperty(key, b ? "true" : "false");
    }

    public void save() {

        try (FileOutputStream fos = new FileOutputStream(new File(path))) {
            properties.store(fos, "** " + dateFormat.format(new Date()));
        } catch (IOException e) {
            log.error(e);
        }
    }
}
