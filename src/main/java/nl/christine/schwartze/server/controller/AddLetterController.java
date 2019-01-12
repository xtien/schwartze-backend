package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.controller.result.AddLetterResult;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin(origins = "http://pengo.christine.nl:3000")
public class AddLetterController  {

    @CrossOrigin(origins = "http://pengo.christine.nl:3000")
    @RequestMapping(method = RequestMethod.POST, value = "/add_letter/")
    public AddLetterResult addLetter(ImportLetter letter) {
        throw new IllegalStateException("not implemented");
    }
}
