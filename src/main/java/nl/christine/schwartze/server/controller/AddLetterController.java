package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.result.AddLetterResult;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class AddLetterController  {

    @CrossOrigin(origins = Application.UI_HOST)
    @PostMapping(value = "/add_letter/")
    public AddLetterResult addLetter(ImportLetter letter) {
        throw new IllegalStateException("not implemented");
    }
}
