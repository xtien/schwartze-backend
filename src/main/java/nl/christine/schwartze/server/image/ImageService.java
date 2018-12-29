package nl.christine.schwartze.server.image;

import java.util.List;

/**
 * User: christine
 * Date: 12/28/18 6:18 PM
 */
public interface ImageService {

    List<String> getImages(int letterNumber);
}
