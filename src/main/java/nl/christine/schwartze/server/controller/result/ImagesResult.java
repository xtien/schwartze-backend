package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Letter;

import java.util.Collection;
import java.util.List;

/**
 * User: christine
 * Date: 12/28/18 6:08 PM
 */
public class ImagesResult extends ApiResult {

    @JsonProperty("images")
    private List<String> images;

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Collection<String> getImages() {
        return images;
    }

}
