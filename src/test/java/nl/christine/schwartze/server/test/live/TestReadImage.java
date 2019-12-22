package nl.christine.schwartze.server.test.live;

import nl.christine.schwartze.server.image.ImageService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * User: christine
 * Date: 12/28/18 6:46 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class TestReadImage {

    @Autowired
    private ImageService imageService;

    @Test
    @Ignore
    public void testReadImage() {

        List<String> result = imageService.getImages(12);

        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }
}
