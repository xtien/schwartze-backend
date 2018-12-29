package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.UpdateLetterController;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.service.LetterService;
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
public class TestUpdateLetterController {

    @Autowired
    private UpdateLetterController updateLetterController;

    @Autowired
    private LetterService letterService;

    private int letterNumber = 12;

    @Test
    public void testUpdateLetter() throws IOException {

        LetterRequest request = new LetterRequest();
        request.setComment("test comment");
        request.setNumber(letterNumber);
        LetterResult result = updateLetterController.updateLetterComment(request).getBody();
        Assert.assertNotNull(result);

        Letter letter = letterService.getLetterByNumber(letterNumber);
        Assert.assertEquals(request.getComment(), letter.getComment());
    }

}
