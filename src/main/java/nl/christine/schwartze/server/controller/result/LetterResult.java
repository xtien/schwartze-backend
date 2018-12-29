package nl.christine.schwartze.server.controller.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.christine.schwartze.server.model.Letter;

public class LetterResult extends ApiResult {

    @JsonProperty("letter")
    private Letter letter;

    @JsonProperty("lettertext")
    private String letterText;

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetterText(String text) {
        this.letterText = text;
    }

    public String getLetterText() {
        return letterText;
    }
}
