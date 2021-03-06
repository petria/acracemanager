package org.freakz.racemanager.racemanager.service.processrunner;


import org.freakz.racemanager.racemanager.events.AliveStatus;
import org.freakz.racemanager.racemanager.events.PushEvent;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;
import org.freakz.racemanager.racemanager.service.ServerConfigManager;
import org.freakz.racemanager.racemanager.service.processrunner.ProcessRunnerImpl.ProcessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static org.freakz.racemanager.racemanager.events.PushEvent.getServerAliveEvent;
import static org.freakz.racemanager.racemanager.service.processrunner.ProcessRunnerImpl.ProcessType.SERVER;
import static org.freakz.racemanager.racemanager.service.processrunner.ProcessRunnerImpl.ProcessType.STRACKER;

@Service
public class ServerControlServiceImpl implements ServerControlService {

    private static final long ALIVE_TIMER_DELAY = 5000L;

    Logger log = LoggerFactory.getLogger(ServerControlServiceImpl.class);

    class ProcessRunnerNode {

        String serverId;

        ProcessRunner serverRunner;

        ProcessRunner strackerRunner;

        ProcessRunnerNode(String serverId) {
            this.serverId = serverId;
        }
    }

    private class MyTimer extends TimerTask {

        @Override
        public void run() {
            handleTimer();
        }
    }


    private final ServerConfigManager serverConfigManager;

    private Timer timer = new Timer();

    private final Map<String, ProcessRunnerNode> processRunnerNodeMap = new HashMap<>();

    @Autowired
    public ServerControlServiceImpl(ServerConfigManager serverConfigManager) {
        this.serverConfigManager = serverConfigManager;
        timer.schedule(new MyTimer(), ALIVE_TIMER_DELAY);
    }

    private void handleTimer() {
        try {
            for (ProcessRunnerNode node : processRunnerNodeMap.values()) {
                PushEvent serverAliveEvent = getServerAliveEvent(node.serverId);
                serverAliveEvent.setServerAlive(AliveStatus.SERVER_NOT_RUNNING);
                serverAliveEvent.setStrackerAlive(AliveStatus.STRACKER_NOT_RUNNING);
                if (node.serverRunner != null) {
                    if (node.serverRunner.isAlive()) {
                        serverAliveEvent.setServerAlive(AliveStatus.SERVER_RUNNING);
                    }
                }
                if (node.strackerRunner != null) {
                    if (node.strackerRunner.isAlive()) {
                        serverAliveEvent.setStrackerAlive(AliveStatus.STRACKER_RUNNING);
                    }
                }
// TODO                Broadcaster.broadcast(serverAliveEvent);
            }
        } catch (Exception e) {
            log.error("Timer failed!", e);
        } finally {
            timer.schedule(new MyTimer(), ALIVE_TIMER_DELAY);
        }
    }

    @Override
    public void handleProcessOutput(String serverId, ProcessType processType, String line) {
        if (processType == SERVER) {
// TODO            Broadcaster.broadcast(getServerConsoleLogEvent(line, serverId));
        } else {
// TODO            Broadcaster.broadcast(getStrackerConsoleLogEvent(line, serverId));
        }
    }


    private ProcessRunnerNode getProcessRunnerNode(String serverId) {
        ProcessRunnerNode node = processRunnerNodeMap.get(serverId);
        if (node == null) {
            node = new ProcessRunnerNode(serverId);
            processRunnerNodeMap.put(serverId, node);
        }
        return node;
    }

    @Override
    public void initializeServer(String serverId) {
        getProcessRunnerNode(serverId);
    }

    @Override
    public void startServer(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);
        ServerStartupPaths config = serverConfigManager.getServerStartupPaths(serverId);

        log.debug("Starting acServer: {}", serverId);
        try {
            if (node.serverRunner != null) {
                log.warn("Stopping old serverRunner");
                node.serverRunner.stopProcess();
                node.serverRunner = null;
            }
            node.serverRunner = new ProcessRunnerImpl(serverId, SERVER, this);
            node.serverRunner.startProcess(config);
        } catch (Exception e) {
            log.error("Starting acServer failed, OS: {}", serverConfigManager.getHostOS(), e);
        }
    }

    @Override
    public void startStracker(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);
        ServerStartupPaths config = serverConfigManager.getDefaultConfig();

        log.debug("Starting Stracker: {}", serverId);
        try {
            if (node.strackerRunner != null) {
                log.warn("Stopping old serverRunner");
                node.strackerRunner.stopProcess();
                node.strackerRunner = null;
            }
            node.strackerRunner = new ProcessRunnerImpl(serverId, STRACKER, this);
            node.strackerRunner.startProcess(config);
        } catch (Exception e) {
            log.error("Starting Stracker failed, OS: {}", serverConfigManager.getHostOS(), e);
        }

    }

    @Override
    public void stopServer(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);
        if (node.serverRunner != null) {
            node.serverRunner.stopProcess();
            node.serverRunner = null;
        }
    }

    @Override
    public String serverStatus(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);
        if (node.serverRunner == null) {
            return "<not running>";
        }
        return "alive: " + node.serverRunner.isAlive();
    }


    @Override
    public void stopStracker(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);
        if (node.strackerRunner != null) {
            node.strackerRunner.stopProcess();
            node.strackerRunner = null;
        }
    }
}
