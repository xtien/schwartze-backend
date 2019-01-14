package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LettersRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.model.Letter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class GetAllLettersController {

    Logger logger = Logger.getLogger(GetAllLettersController.class);

    @Autowired
    private LetterDao dao;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_letters/")
    public ResponseEntity<LettersResult> getLetters(@RequestBody LettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = dao.getLetters();
            result.setLetters(letters);
            result.setResult(LettersResult.OK);

        } catch (Exception e) {
            logger.error(e);
         }

        return new ResponseEntity<LettersResult>(result, HttpStatus.OK);
    }
}
