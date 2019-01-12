package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.request.PeopleRequest;
import nl.christine.schwartze.server.controller.request.PersonRequest;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.controller.result.PeopleResult;
import nl.christine.schwartze.server.controller.result.PersonResult;
import nl.christine.schwartze.server.dao.PersonDao;
import nl.christine.schwartze.server.model.Person;
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
@CrossOrigin(origins = Application.UI_HOST)
public class GetPeopleController {

    @Autowired
    private PersonDao dao;

    @CrossOrigin(origins = Application.UI_HOST)
    @RequestMapping(method = RequestMethod.POST, value = "/get_people_details/")
    @Transactional("transactionManager")
    public ResponseEntity<PeopleResult> getPeople(@RequestBody PeopleRequest request) {

        PeopleResult result = new PeopleResult();
        result.setResult(LettersResult.NOT_OK);

        try {

            List<Person> people = dao.getPeople(request.getIds());
            if (people != null) {
                result.setPeople(people);
                result.setResultCode(LettersResult.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<PeopleResult>(result, HttpStatus.OK);
    }
}
