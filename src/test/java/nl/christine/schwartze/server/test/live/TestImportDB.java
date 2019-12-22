package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.admin.ImportDBController;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.dao.LocationDao;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.model.MyLocation;
import nl.christine.schwartze.server.model.Person;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestImportDB {

    @Autowired
    ImportDBController importDBController;

    @Autowired
    LetterDao letterDao;

    @Autowired
    PersonDao personDao;

    @Autowired
    LocationDao locationDao;

    @Test
    @Ignore
    public void importDB() {

        LettersResult result = importDBController.importDB();

        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.getResultCode());

        List<Letter> letters = letterDao.getLetters();
        Assert.assertNotNull(letters);
        Assert.assertEquals(566, letters.size());

        List<Person> people = personDao.getPersons();
        Assert.assertNotNull(people);
        Assert.assertEquals(267, people.size());

        List<MyLocation> locations = locationDao.getLocations();
        Assert.assertNotNull(locations);
        Assert.assertEquals(161, locations.size());
    }
}
