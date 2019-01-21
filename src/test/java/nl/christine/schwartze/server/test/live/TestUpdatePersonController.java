package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.UpdatePersonController;
import nl.christine.schwartze.server.controller.request.UpdatePersonRequest;
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
import java.util.Random;

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
    private String testComment = "just commenting ";

    @Test
    public void testUpdatePerson() throws IOException {

        testComment += new Random().nextInt(999);
        Person p = new Person();
        p.setId(personId);
        p.setComment(testComment);
        UpdatePersonRequest request = new UpdatePersonRequest();
        request.setPerson(p);
        request.setId(personId);
        PersonResult result = updatePersonController.updatePerson(request).getBody();
        Assert.assertNotNull(result);

        Person person = personService.getPerson(personId);
        Assert.assertEquals(testComment, person.getComment());
    }

}
