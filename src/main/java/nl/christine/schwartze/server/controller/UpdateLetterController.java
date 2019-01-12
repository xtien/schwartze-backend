package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.LetterRequest;
import nl.christine.schwartze.server.controller.request.PersonRequest;
import nl.christine.schwartze.server.controller.result.LetterResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Letter;
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

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UpdateLetterController {

    @Autowired
    private LetterService letterService;

    @CrossOrigin(origins = Application.UI_HOST)
    @RequestMapping(method = RequestMethod.POST, value = "/update_letter_details/")
    @Transactional("transactionManager")
    public ResponseEntity<LetterResult> updateLetterComment(@RequestBody LetterRequest request) {

        LetterResult result = new LetterResult();
        result.setResult(LetterResult.NOT_OK);

        try {
            Letter letter = letterService.getLetterByNumber(request.getLetterNumber());
            if (letter != null) {
                letter.setComment(request.getComment());
                result.setResultCode(LetterResult.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
