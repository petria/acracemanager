package org.freakz.racemanager.racemanager.service.processrunner;

public interface ServerControlService {

    void handleProcessOutput(String serverId, ProcessRunnerImpl.ProcessType processType, String line);

    void startServer(String id);

    void stopServer(String id);

    String serverStatus(String id);

    void startStracker(String id);

    void stopStracker(String id);

}
