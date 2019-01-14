package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UpdateLocationController {

    Logger logger = Logger.getLogger(UpdateLocationController.class);

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/update_location_details/")
    @Transactional("transactionManager")
    public ResponseEntity<LocationResult> updateLocationComment(@RequestBody LocationRequest request) {

        LocationResult result = new LocationResult();
        result.setResult(PersonResult.NOT_OK);

        try {
            MyLocation location = locationService.getLocation(request.getId());
            if (location != null) {
                location.setComment(request.getComment());
                result.setResultCode(LocationResult.OK);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
