package nl.christine.schwartze.server;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;

public class EngineLauncher implements Daemon {

    private static final Logger log = Logger.getLogger(EngineLauncher.class);

    private static SchwartzeServer server = null;

    @Override
    public void destroy() {
        log.debug("Daemon destroy");
    }

    @Override
    public void init(DaemonContext arg0) throws DaemonInitException, Exception {
        log.debug("Daemon init");
    }

    @Override
    public void start() throws Exception {
        log.debug("Daemon start");
        initialize();
    }

    @Override
    public void stop() throws Exception {
        log.debug("Daemon stop");
        terminate();
    }

    private void initialize() {

        if (server == null) {

            log.info("Starting the Engine");
            server = new SchwartzeServer();

            try {

                server.startIt();

            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
            }
        }
    }

    private void terminate() {

        if (server != null) {
            log.info("Stopping the Engine");

            try {
                server.stop();
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
            }

            log.info("Engine stopped");
        }
    }
}
