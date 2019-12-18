package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.LetterGetAllController;
import nl.christine.schwartze.server.controller.admin.ImportDBController;
import nl.christine.schwartze.server.controller.request.LettersRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class TestGetLetters {

    @Autowired
    private LetterGetAllController getLetterController;

    @Autowired
    private ImportDBController importDBController;

    private static LettersResult importResult;

    @Before
    public void before() {
        importResult = importDBController.importDB();
    }

    @Test
    public void testGetLetters() {


        Assert.assertNotNull(importResult);
        Assert.assertEquals(0, importResult.getResultCode());


        LettersRequest request = new LettersRequest();

        ResponseEntity<LettersResult> result = getLetterController.getLetters(request);

        Assert.assertNotNull(result);
        Assert.assertEquals(566, result.getBody().getLetters().size());
    }
}
