package nl.christine.schwartze.server.service;

import nl.christine.schwartze.server.model.MyLocation;

/**
 * User: christine
 * Date: 12/29/18 12:26 PM
 */
public interface LocationService {

    int updateLocationComment(MyLocation location);

    MyLocation getLocation(int id);
}
