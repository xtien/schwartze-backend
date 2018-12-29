package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.request.PersonRequest;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.model.Person;
import nl.christine.schwartze.server.service.PersonService;
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
@CrossOrigin(origins = "http://pengo.christine.nl:3000")
public class UpdatePersonController {

    @Autowired
    private PersonService personService;

    @CrossOrigin(origins = "http://pengo.christine.nl:3000")
    @RequestMapping(method = RequestMethod.POST, value = "/update_person_details/")
    @Transactional("transactionManager")
    public ResponseEntity<PersonResult> updatePersonComment(@RequestBody PersonRequest request) {

        PersonResult result = new PersonResult();
        result.setResult(PersonResult.NOT_OK);

        try {
            Person person = personService.getPerson(request.getId());
            if (person != null) {
                person.setComment(request.getComment());
                result.setResultCode(PersonResult.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
