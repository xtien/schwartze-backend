package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.LocationService;
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
 * Date: 12/29/18 12:28 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestUpdateLocation {

    @Autowired
    private LocationService locationService;

    @Test
    public void testGetLetters() throws IOException {

        MyLocation location  = locationService.getLocation(12);

        String newComment = location.getComment() + " more";
        location.setComment(newComment);

        locationService.updateLocationComment(location);

        MyLocation resultingLocation = locationService.getLocation(12);

        Assert.assertNotNull(resultingLocation);
        Assert.assertEquals(newComment, resultingLocation.getComment());
    }

}

