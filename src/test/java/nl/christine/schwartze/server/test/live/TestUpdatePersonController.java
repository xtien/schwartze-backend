package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.UpdatePersonController;
import nl.christine.schwartze.server.controller.request.PersonRequest;
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
public class TestUpdatePersonController {

    @Autowired
    private UpdatePersonController updatePersonController;

    @Autowired
    private PersonService personService;

    private int personId = 12;

    @Test
    public void testUpdatePerson() throws IOException {

        PersonRequest request = new PersonRequest();
        request.setComment("test comment");
        request.setId(personId);
        PersonResult result = updatePersonController.updatePersonComment(request).getBody();
        Assert.assertNotNull(result);

        Person person = personService.getPerson(personId);
        Assert.assertEquals(request.getComment(), person.getComment());
    }

}
