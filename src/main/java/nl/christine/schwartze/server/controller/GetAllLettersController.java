package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.LettersRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.model.Letter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://pengo.christine.nl:3000")
public class GetAllLettersController {

    @Autowired
    private LetterDao dao;

    @CrossOrigin(origins = "http://pengo.christine.nl:3000")
    @RequestMapping(method = RequestMethod.POST, value = "/get_letters/")
    @Transactional("transactionManager")
    public ResponseEntity<LettersResult> getLetters(@RequestBody LettersRequest request) {

        LettersResult result = new LettersResult();

        try {

            List<Letter> letters = dao.getLetters();
            result.setLetters(letters);
            result.setResult(LettersResult.OK);

        } catch (Exception e) {
            e.printStackTrace();
         }

        return new ResponseEntity<LettersResult>(result, HttpStatus.OK);
    }
}
