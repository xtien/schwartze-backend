package nl.christine.schwartze.server.properties;

import nl.christine.schwartze.server.ServerConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class SchwartzeProperties {

    private static final Logger log = Logger.getLogger(SchwartzeProperties.class);

    private static Properties properties;
    private static String path;
    private static FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy/MM/dd hh:mm");

    private static boolean propertiesRead = false;

    public static void init() {
        if (!propertiesRead) {
            readProperties();
            propertiesRead = true;
        }
    }

    private static void readProperties() {

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

    public static boolean containsKey(Object key) {
        return properties.containsKey(key);
    }

    public static boolean hasProperty(Object key) {
        return containsKey(key);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key) {
        String stringProp = getProperty(key);
        int result = -1;
        if (NumberUtils.isCreatable(stringProp)) {
            result = NumberUtils.toInt(stringProp);
        }
        return result;
    }

    public static boolean getBooleanProperty(String key) {
        String stringProp = getProperty(key);
        return Boolean.parseBoolean(stringProp);
    }

    public static void setBooleanProperty(String key, boolean b) {
        properties.setProperty(key, b ? "true" : "false");
    }

    public static void save() {

        try (FileOutputStream fos = new FileOutputStream(new File(path))) {
            properties.store(fos, "** " + dateFormat.format(new Date()));
        } catch (IOException e) {
            log.error(e);
        }
    }
}
