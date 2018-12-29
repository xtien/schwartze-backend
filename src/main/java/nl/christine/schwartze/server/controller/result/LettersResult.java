package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Letter;

import java.util.Collection;
import java.util.List;

public class LettersResult extends ApiResult {

    private int result;

    @JsonProperty("letters")
    private List<Letter> letters;

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }

    public Collection<Letter> getLetters() {
        return letters;
    }
}
