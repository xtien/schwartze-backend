package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.LetterGetController;
import nl.christine.schwartze.server.controller.admin.ImportDBController;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TestGetLetter {

    @Autowired
    private LetterGetController getLetterController;

    @Autowired
    private ImportDBController importDBController;

    private static LettersResult importResult;

    @Before
    public void before() {
        importResult = importDBController.importDB();
    }

    @Test
    public void testGetLetters() throws IOException {

        LetterRequest request = new LetterRequest();
        request.setNumber(24);

        ResponseEntity<LetterResult> response = getLetterController.getLetter(request);
        LetterResult result = response.getBody();

        Assert.assertNotNull(result);
        Assert.assertEquals(24, result.getLetter().getNumber());
        Assert.assertEquals("Deelman", result.getLetter().getSenders().get(0).getLastName());
        Assert.assertTrue(100 < result.getLetterText().length());
    }
}
