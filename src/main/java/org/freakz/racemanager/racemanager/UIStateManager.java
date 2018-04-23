package org.freakz.racemanager.racemanager;

import org.freakz.racemanager.racemanager.model.ServerConfig;
import org.freakz.racemanager.racemanager.model.ServerConfigValidation;

public interface UIStateManager {

    void startServer();

    void stopServer();

    void startStracker();

    void stopStracker();

    ServerConfig getServerConfig(String serverId);

    ServerConfigValidation validateServerConfig(ServerConfig serverConfig);

    void setServerConfig(String serverId, ServerConfig serverConfig);

}
