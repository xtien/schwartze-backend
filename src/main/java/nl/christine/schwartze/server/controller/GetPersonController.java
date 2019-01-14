package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.PersonRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class GetPersonController {

    Logger logger = Logger.getLogger(GetPersonController.class);

    @Autowired
    private PersonService personService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/get_person_details/")
    @Transactional("transactionManager")
    public ResponseEntity<PersonResult> getPerson(@RequestBody PersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(LettersResult.NOT_OK);

        try {

            Person person = personService.getPerson(request.getId());
            if (person != null) {
                result.setPerson(person);
                result.setResultCode(LettersResult.OK);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
