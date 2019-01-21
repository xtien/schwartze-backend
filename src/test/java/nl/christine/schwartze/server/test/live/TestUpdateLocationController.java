package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.UpdateLocationController;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.request.UpdateLocationRequest;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.service.LocationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * User: christine
 * Date: 12/29/18 12:17 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestUpdateLocationController {

    @Autowired
    private UpdateLocationController updateLocationController;

    @Autowired
    private LocationService locationService;

    private int locationId = 12;
    private String locationName = "Hilversum";
    private String testDescription = "just testing";
    private String testComment = "just commenting";

    @Test
    public void testUpdateLocation() throws IOException {

        UpdateLocationRequest request = new UpdateLocationRequest();
        MyLocation myLocation = new MyLocation();
        myLocation.setDescription(testDescription);
        myLocation.setComment(testComment);
        myLocation.setId(locationId);
        request.setLocation(myLocation);
        LocationResult result = updateLocationController.updateLocation(request).getBody();
        Assert.assertNotNull(result);

        MyLocation location = locationService.getLocation(locationId);
        Assert.assertEquals(testDescription, location.getDescription());
        Assert.assertEquals(testComment, location.getComment());
    }

}
