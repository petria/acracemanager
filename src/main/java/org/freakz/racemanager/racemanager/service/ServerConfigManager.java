package org.freakz.racemanager.racemanager.service;

import org.freakz.racemanager.racemanager.model.HostOS;
import org.freakz.racemanager.racemanager.model.ServerConfig;
import org.freakz.racemanager.racemanager.model.ServerConfigValidation;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;

public interface ServerConfigManager {

    ServerStartupPaths getDefaultConfig();

    ServerStartupPaths getServerStartupPaths(String serverId);

    HostOS getHostOS();

    ServerConfigValidation validateServerConfig(ServerConfig serverConfig);

    void setServerConfig(String serverId, ServerConfig serverConfig);

    ServerConfig getServerConfig(String serverId);

}
