package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.UpdatePersonRequest;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * User: christine
 * Date: 12/29/18 12:41 PM
 */
@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class UpdatePersonController {

    Logger logger = Logger.getLogger(UpdatePersonController.class);

    @Autowired
    private PersonService personService;

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/update_person_details/")
    public ResponseEntity<PersonResult> updatePersonComment(@RequestBody UpdatePersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(PersonResult.NOT_OK);

        try {
            Person person = personService.getPerson(request.getId());
            if (person != null) {
                person.setComment(request.getPerson().getComment());
                person.setLinks(request.getPerson().getLinks());
                person.setComment(request.getPerson().getComment());
                result.setResultCode(PersonResult.OK);
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
