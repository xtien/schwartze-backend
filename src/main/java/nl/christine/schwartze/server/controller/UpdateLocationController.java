package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.request.PersonRequest;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LocationService;
import nl.christine.schwartze.server.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UpdateLocationController {

    @Autowired
    private LocationService locationService;

    @CrossOrigin(origins = Application.UI_HOST)
    @RequestMapping(method = RequestMethod.POST, value = "/update_location_details/")
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
            e.printStackTrace();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
