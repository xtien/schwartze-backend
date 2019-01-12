package nl.christine.schwartze.server;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * User: christine
 * Date: 12/16/18 10:06 PM
 */
@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class Application {

    public static final String UI_HOST = "http://pengo.christine.nl:3000";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
