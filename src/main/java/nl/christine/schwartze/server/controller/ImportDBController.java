package nl.christine.schwartze.server.controller;

import nl.christine.schwartze.server.Application;
import nl.christine.schwartze.server.controller.result.LettersResult;
import nl.christine.schwartze.server.modelimport.ImportLetter;
import nl.christine.schwartze.server.service.LetterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@CrossOrigin(origins = Application.UI_HOST)
public class ImportDBController {

    @Autowired
    private LetterService letterService;

    private Logger logger = Logger.getLogger(ImportDBController.class);

    public LettersResult importDB() {

        LettersResult result = new LettersResult();

        List<ImportLetter> importLetters;

        try {

            importLetters = letterService.getImportLetters();

        } catch (Exception e) {
            logger.error(e);
            result.setResult(-1);
            return result;
        }



        for (ImportLetter importLetter : importLetters) {

            try {

                letterService.persistIfNotPresent(importLetter);

            } catch (Exception e) {
                logger.error(e);
                result.setResult(-1);
                return result;
            }
        }

        return result;
    }
}
