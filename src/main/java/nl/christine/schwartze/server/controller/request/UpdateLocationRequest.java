package nl.christine.schwartze.server.controller.request;

import nl.christine.schwartze.server.model.MyLocation;

public class UpdateLocationRequest {

    public UpdateLocationRequest() {
    }

    private MyLocation location;


    public MyLocation getLocation() {
        return location;
    }

    public void setLocation(MyLocation location) {
        this.location = location;
    }
}
