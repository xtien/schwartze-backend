package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.service.LetterService;
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

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class GetLetterController {

    private final String lettersDirectory;
    private final String textDocumentName;

    @Autowired
    private LetterService letterService;

    public GetLetterController() {

        SchwartzeProperties.init();
        lettersDirectory = SchwartzeProperties.getProperty("letters_directory");
        textDocumentName = SchwartzeProperties.getProperty("text_document_name");
    }

    @CrossOrigin(origins = Application.UI_HOST)
    @RequestMapping(method = RequestMethod.POST, value = "/get_letter_details/")
    public ResponseEntity<LetterResult> getLetter(@RequestBody LetterRequest request) throws IOException {

        LetterResult result = new LetterResult();

        try {

            Letter letter = letterService.getLetterByNumber(request.getLetterNumber());
            result.setLetter(letter);
            result.setResult(LettersResult.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        result.setLetterText(getLetterText(request.getLetterNumber()));

        return new ResponseEntity<LetterResult>(result, HttpStatus.OK);
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
