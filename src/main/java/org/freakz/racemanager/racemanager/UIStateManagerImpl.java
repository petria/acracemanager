package org.freakz.racemanager.racemanager;

import lombok.extern.slf4j.Slf4j;
import org.freakz.racemanager.racemanager.service.processrunner.ServerControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UIStateManagerImpl implements UIStateManager {

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
        serverControlService.startStracker("airiot.fi");
    }

    @Override
    public void stopStracker() {
        serverControlService.stopStracker("airiot.fi");
    }
}