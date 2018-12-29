package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.ImagesRequest;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.ImagesResult;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.image.ImageService;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
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
@CrossOrigin(origins = "http://pengo.christine.nl:3000")
public class GetLetterImagesController {

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

    @CrossOrigin(origins = "http://pengo.christine.nl:3000")
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

        String fileName = lettersDirectory + "/" + letterNumber + "/" + textDocumentName;
        BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        String result = "";
        String line = "";
        while ((line = rd.readLine()) != null) {
            result += "<br>" + line;
        }
        int i = 1;
        result = result.replaceAll("    ", "&nbsp&nbsp&nbsp&nbsp;");
        result = result.replaceAll("/", "<BR><BR><i>blad " + ++i + "</i><BR><BR>");

        return result;
    }
}
