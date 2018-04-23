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
    public void startServer() {
        log.debug("Handle server start!");
        serverControlService.startServer("airiot.fi");
    }

    @Override
    public void stopServer() {
        log.debug("Handle server stop!");
        serverControlService.stopServer("airiot.fi");
    }

    @Override
    public void startStracker() {
        log.debug("Handle start Stracker");
        serverControlService.startStracker("airiot.fi");
    }

    @Override
    public void stopStracker() {
        log.debug("Handle stop Stracker");
        serverControlService.stopStracker("airiot.fi");
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
