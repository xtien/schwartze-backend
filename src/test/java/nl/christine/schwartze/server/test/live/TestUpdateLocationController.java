package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.UpdateLocationController;
import nl.christine.schwartze.server.controller.request.LocationRequest;
import nl.christine.schwartze.server.controller.result.LocationResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
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
    private PersonService personService;

    private int locationId = 12;

    @Test
    public void testUpdateLocation() throws IOException {

        LocationRequest request = new LocationRequest();
        request.setComment("test comment");
        request.setId(locationId);
        LocationResult result = updateLocationController.updateLocationComment(request).getBody();
        Assert.assertNotNull(result);

        Person person = personService.getPerson(locationId);
        Assert.assertEquals(request.getComment(), person.getComment());
    }

}
