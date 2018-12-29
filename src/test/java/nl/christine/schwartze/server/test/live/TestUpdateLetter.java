package nl.christine.schwartze.server.test.live;

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
 * Date: 12/29/18 12:02 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestUpdateLetter {

    @Autowired
    private LetterService letterService;

    @Test
    public void testGetLetters() throws IOException {

        Letter letter = letterService.getLetterByNumber(12);

        String newComment = letter.getComment() + " more";
        letter.setComment(newComment);

        letterService.updateLetterComments(letter);

        Letter resultingLetter = letterService.getLetterByNumber(12);

        Assert.assertNotNull(resultingLetter);
        Assert.assertEquals(newComment,resultingLetter.getComment());
    }
}
