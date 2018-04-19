package org.freakz.racemanager.racemanager.service.processrunner;

public interface ServerControlService {

    void startServer(String id);

    void stopServer(String id);

    String serverStatus(String id);

    void serverLineAddedToStdout(String line);

    void startStracker(String id);

    void stopStracker(String id);

}
