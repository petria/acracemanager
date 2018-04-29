package org.freakz.racemanager.racemanager;

import org.freakz.racemanager.racemanager.model.ServerConfig;
import org.freakz.racemanager.racemanager.model.ServerConfigValidation;

public interface UIStateManager {

    void initializeServer(String serverId);

    void startServer(String serverId);

    void stopServer(String serverId);

    void startStracker(String serverId);

    void stopStracker(String serverId);

    ServerConfig getServerConfig(String serverId);

    ServerConfigValidation validateServerConfig(ServerConfig serverConfig);

    void setServerConfig(String serverId, ServerConfig serverConfig);

}
