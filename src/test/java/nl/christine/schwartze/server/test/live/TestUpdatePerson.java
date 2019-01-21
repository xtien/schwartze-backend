package nl.christine.schwartze.server.test.live;

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
public class TestUpdatePerson {

    @Autowired
    private PersonService personService;

    @Test
    public void testGetLetters() throws IOException {

        Person person = personService.getPerson(12);

        String newComment = person.getComment() + " more";
        person.setComment(newComment);

        personService.updatePerson(person);

        Person resultingPerson = personService.getPerson(12);

        Assert.assertNotNull(resultingPerson);
        Assert.assertEquals(newComment, resultingPerson.getComment());
    }

}
