package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.pub.PeopleGetController;
import nl.christine.schwartze.server.controller.admin.ImportDBController;
import nl.christine.schwartze.server.controller.request.PeopleRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * User: christine
 * Date: 12/25/18 5:09 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class TestGetPeople {

    @Autowired
    private PeopleGetController peopleGetController;

    private List<Integer> ids = new ArrayList<>();

    @Autowired
    private ImportDBController importDBController;

    private static LettersResult importResult;

    @Before
    public void before() {
        importResult = importDBController.importDB();
    }

    @Test
    public void testGetPeople(){

        ids.add(1);
        ids.add(2);
        PeopleRequest request = new PeopleRequest();
        request.setIds(ids);

        List<Person> people = peopleGetController.getPeople(request).getBody().getPeople();

        Assert.assertNotNull(people);
        Assert.assertEquals(2,people.size());
        Assert.assertEquals("Cobi", people.get(1).getFirstName());
    }
}
