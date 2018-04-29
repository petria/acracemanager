package org.freakz.racemanager.racemanager.service.processrunner;

public interface ServerControlService {

    void handleProcessOutput(String serverId, ProcessRunnerImpl.ProcessType processType, String line);

    void startServer(String serverId);

    void stopServer(String serverId);

    String serverStatus(String serverId);

    void startStracker(String serverId);

    void stopStracker(String serverId);

    void initializeServer(String serverId);
}
