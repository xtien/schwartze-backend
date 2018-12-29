package nl.christine.schwartze.server.service.impl;

import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: christine
 * Date: 12/29/18 12:26 PM
 */
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Override
    @Transactional("transactionManager")
    public int updateLocationComment(MyLocation location) {
        int result = -1;
        try {
            MyLocation existingLocation = locationDao.getLocation(location.getId());
            existingLocation.setComment(location.getComment());
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public MyLocation getLocation(int i) {
        return locationDao.getLocation(i);
    }

}
