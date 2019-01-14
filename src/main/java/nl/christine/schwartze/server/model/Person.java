package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "people")
@EnableJpaRepositories(
        basePackages = "nl.christine.schwartze.server.dao",
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "defaultPU")
public class Person {

    public static final String FIRST_NAME = "first_name";
    public static final String MIDDLE_NAME = "middle_name";
    public static final String LAST_NAME = "last_name";
    public static final String COMMENT = "comment";
    public static final String LINKS = "links";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = FIRST_NAME)
    @JsonProperty(FIRST_NAME)
    private String firstName;

    @Column(name = MIDDLE_NAME)
    @JsonProperty(MIDDLE_NAME)
    private String middleName;

    @Column(name = LAST_NAME)
    @JsonProperty(LAST_NAME)
    private String lastName;

    @Column(name = COMMENT)
    @JsonProperty(COMMENT)
    private String comment;

    @ManyToMany(mappedBy = "senders", cascade = CascadeType.ALL)
    private List<Letter> lettersWritten = new ArrayList<>();

    @ManyToMany(mappedBy = "recipients", cascade = CascadeType.ALL)
    private List<Letter> lettersReceived = new ArrayList<>();

    @Column(name = LINKS)
    @ElementCollection(targetClass = String.class)
    private List<String> links = new ArrayList<>();

    public Person() {
        // used for deserialization
    }

    public void setFirstName(String s) {
        firstName = s;
    }

    public void setMiddleName(String s) {
        middleName = s;
    }

    public void setLastName(String substring) {
        lastName = substring;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addLetterWritten(Letter letter) {
        lettersWritten.add(letter);
    }

    public void addLetterReceived(Letter letter) {
        lettersReceived.add(letter);
    }

    public void noNulls() {
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (middleName == null) {
            middleName = "";
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
