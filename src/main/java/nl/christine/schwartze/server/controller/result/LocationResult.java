package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.MyLocation;

public class LocationResult extends ApiResult {

    @JsonProperty("location")
    private MyLocation location;

    public void setLocation(MyLocation location) {
        this.location = location;
    }
}
