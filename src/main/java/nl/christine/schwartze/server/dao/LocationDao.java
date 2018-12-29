package nl.christine.schwartze.server.dao;

import nl.christine.schwartze.server.model.MyLocation;

import java.util.List;

public interface LocationDao {

    List<MyLocation> getLocations();

    MyLocation getLocationByName(MyLocation location);

    MyLocation getLocation(int id);
}
