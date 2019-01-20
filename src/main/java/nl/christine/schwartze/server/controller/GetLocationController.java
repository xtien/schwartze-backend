package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * User: christine
 * Date: 1/20/19 6:21 PM
 */
@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class GetLocationController {
    Logger logger = Logger.getLogger(GetLocationController.class);

    @Autowired
    private LocationService locationService;

    public GetLocationController() {
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_location/")
    public ResponseEntity<LocationResult> getLocation(@RequestBody LocationRequest request) throws IOException {

        LocationResult result = new LocationResult();

        try {

            MyLocation location = locationService.getLocation(request.getId());
            result.setLocation(location);
            result.setResult(LettersResult.OK);

        } catch (Exception e) {
            logger.error(e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
