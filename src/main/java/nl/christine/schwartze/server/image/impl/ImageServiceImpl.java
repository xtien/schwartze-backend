package nl.christine.schwartze.server.image.impl;

import nl.christine.schwartze.server.image.ImageService;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: christine
 * Date: 12/28/18 6:18 PM
 */
public class ImageServiceImpl implements ImageService {

    private final String imagesDirectory;

    private Logger logger = Logger.getLogger(ImageServiceImpl.class);

    public ImageServiceImpl() {
        imagesDirectory = SchwartzeProperties.getProperty("images_directory");
    }

    @Override
    public List<String> getImages(int letterNumber) {

         return Arrays.stream(new File(imagesDirectory + "/" + letterNumber + "/").listFiles())
                .filter((file -> file.getName().toLowerCase().endsWith(".jpg")))
                .map(file -> createBlob(file))
                .collect(Collectors.toList());
    }

    private String createBlob(File imageFile) {

       String resultBlob = "";

        try {
            resultBlob = new String(Base64.encodeBase64(IOUtils.toByteArray(new FileInputStream(imageFile))), "UTF-8");
        } catch (FileNotFoundException fnfe) {
            logger.error(fnfe);
        } catch (IOException ioe) {
            logger.error(ioe);
        }

        return resultBlob;
    }
}
