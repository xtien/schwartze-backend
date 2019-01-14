package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.ImagesRequest;
import nl.christine.schwartze.server.controller.result.ImagesResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.image.ImageService;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class GetLetterImagesController {

    Logger logger = Logger.getLogger(GetLetterImagesController.class);

    private final String lettersDirectory;
    private final String textDocumentName;

    @Autowired
    private LetterDao dao;

    @Autowired
    private ImageService imageService;

    public GetLetterImagesController() {

        SchwartzeProperties.init();
        lettersDirectory = SchwartzeProperties.getProperty("letters_directory");
        textDocumentName = SchwartzeProperties.getProperty("text_document_name");
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @RequestMapping(method = RequestMethod.POST, value = "/get_letter_images/")
    @Transactional("transactionManager")
    public ResponseEntity<ImagesResult> getLetterImages(@RequestBody ImagesRequest request) throws IOException {

        ImagesResult result = new ImagesResult();

        try {

            List<String> images = imageService.getImages(request.getLetterNumber());
            if(images.size() <1 ){
                return new ResponseEntity<ImagesResult>(result, HttpStatus.NOT_FOUND);
            }
            result.setImages(images);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ImagesResult>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ImagesResult>(result, HttpStatus.OK);
    }

    private String getLetterText(int letterNumber) throws IOException {

        String result = "";
        String fileName = lettersDirectory + "/" + letterNumber + "/" + textDocumentName;
        try(BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {

            String line = "";
            while ((line = rd.readLine()) != null) {
                result += "<br>" + line;
            }
            int i = 1;
            result = result.replaceAll("    ", "&nbsp&nbsp&nbsp&nbsp;");
            result = result.replaceAll("/", "<BR><BR><i>blad " + ++i + "</i><BR><BR>");
        } catch(Exception e){
            logger.error(e);
        }

        return result;
    }
}
