package org.freakz.racemanager.racemanager.service;

import org.freakz.racemanager.racemanager.model.HostOS;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;

public interface ServerConfigManager {

    ServerStartupPaths getDefaultConfig();

    HostOS getHostOS();
}
