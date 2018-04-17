package org.freakz.racemanager.racemanager.service;

public interface ServerControlService {

    void startServer(String id);

    void stopServer(String id);

    String serverStatus(String id);

    void lineAddedToStdout(String line);

}
