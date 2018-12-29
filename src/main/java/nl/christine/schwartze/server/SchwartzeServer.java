package nl.christine.schwartze.server;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SchwartzeServer {

    Logger log = Logger.getLogger(SchwartzeServer.class);

    public void startIt()  {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

       LetterDao dao = context.getBean(LetterDao.class);

        SchwartzeProperties.init();

//        params.add("sslContextFactory", "org.restlet.engine.ssl.DefaultSslContextFactory");
//        params.add("keystorePath", SchwartzeProperties.getProperty("keystore_path"));
//        params.add("keystorePassword", SchwartzeProperties.getProperty("keystore_password"));
//        params.add("keystoreType", SchwartzeProperties.getProperty("keystore_type"));
//        params.add("keyAlias", SchwartzeProperties.getProperty("key_alias"));
//        params.add("keyPassword", SchwartzeProperties.getProperty("key_password"));
    }

    public void stop() {

    }

    public static void main(final String[] args) {

        try {
            new SchwartzeServer().startIt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
