package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.controller.GetAllLettersController;
import nl.christine.schwartze.server.controller.GetLetterImagesController;
import nl.christine.schwartze.server.controller.request.ImagesRequest;
import nl.christine.schwartze.server.controller.request.LettersRequest;
import nl.christine.schwartze.server.controller.result.ImagesResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TestGetImageController {

    @Autowired
    private GetLetterImagesController getLetterImagesController;

    @Test
    public void testGetLetters() throws IOException {

        ImagesRequest request = new ImagesRequest();
        request.setNumber(12);

        ResponseEntity<ImagesResult> result = getLetterImagesController.getLetterImages(request);

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.getBody().getImages().size());
     }
}
