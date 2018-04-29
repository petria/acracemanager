package org.freakz.racemanager.racemanager;

import org.freakz.racemanager.racemanager.model.ServerConfig;
import org.freakz.racemanager.racemanager.model.ServerConfigValidation;
import org.freakz.racemanager.racemanager.service.ServerConfigManager;
import org.freakz.racemanager.racemanager.service.processrunner.ServerControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UIStateManagerImpl implements UIStateManager {

    Logger log = LoggerFactory.getLogger(UIStateManagerImpl.class);

    @Autowired
    private ServerConfigManager serverConfigManager;

    @Autowired
    private ServerControlService serverControlService;

    @Override
    public void initializeServer(String serverId) {
        log.debug("Handle server start!");
        serverControlService.initializeServer(serverId);
    }


    @Override
    public void startServer(String serverId) {
        log.debug("Handle server start!");
        serverControlService.startServer(serverId);
    }

    @Override
    public void stopServer(String serverId) {
        log.debug("Handle server stop!");
        serverControlService.stopServer(serverId);
    }

    @Override
    public void startStracker(String serverId) {
        log.debug("Handle start Stracker");
        serverControlService.startStracker(serverId);
    }

    @Override
    public void stopStracker(String serverId) {
        log.debug("Handle stop Stracker");
        serverControlService.stopStracker(serverId);
    }

    @Override
    public ServerConfig getServerConfig(String serverId) {
        return serverConfigManager.getServerConfig(serverId);
    }

    @Override
    public void setServerConfig(String serverId, ServerConfig serverConfig) {
        serverConfigManager.setServerConfig(serverId, serverConfig);
    }

    @Override
    public ServerConfigValidation validateServerConfig(ServerConfig serverConfig) {
        return serverConfigManager.validateServerConfig(serverConfig);
    }

}
