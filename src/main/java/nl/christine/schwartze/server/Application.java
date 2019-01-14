package nl.christine.schwartze.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

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
