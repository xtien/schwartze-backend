package nl.christine.schwartze.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
@EnableJpaRepositories(
        basePackages = "nl.christine.schwartze.server.dao",
       transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "defaultPU")
public class MyLocation {

    public static final String LOCATION_NAME = "location_name";
    public static final String DESCRIPTION = "description";
    public static final String LINKS = "links";
    public static final String COMMENT = "comment";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = LOCATION_NAME)
    @JsonProperty(LOCATION_NAME)
    private String locationName;

    @Column(name = DESCRIPTION)
    @JsonProperty(DESCRIPTION)
    private String description;

    @ManyToMany(mappedBy = "fromLocations", cascade = CascadeType.ALL)
    private List<Letter> lettersFrom = new ArrayList<>();

    @ManyToMany(mappedBy = "toLocations", cascade = CascadeType.ALL)
    private List<Letter> lettersTo = new ArrayList<>();

    @Column(name = LINKS)
    @ElementCollection(targetClass = String.class)
    private List<String> links = new ArrayList<>();

    @Column(name = COMMENT)
    @JsonProperty(COMMENT)
    private String comment;

    public MyLocation() {
    }

    public MyLocation(String name) {
        this.locationName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return locationName;
    }

    public void setLetterFrom(Letter letter) {
        lettersFrom.add(letter);
    }

    public void setLetterTo(Letter letter) {
        lettersTo.add(letter);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
