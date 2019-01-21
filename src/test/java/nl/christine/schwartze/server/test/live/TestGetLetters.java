package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.GetAllLettersController;
import nl.christine.schwartze.server.controller.request.LettersRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestGetLetters {

    @Autowired
    private GetAllLettersController getLettersResource;

    @Test
    public void testGetLetters() {

        LettersRequest request = new LettersRequest();

        ResponseEntity<LettersResult> result = getLettersResource.getLetters(request);

        Assert.assertNotNull(result);
        Assert.assertEquals(336, result.getBody().getLetters().size());
    }
}
