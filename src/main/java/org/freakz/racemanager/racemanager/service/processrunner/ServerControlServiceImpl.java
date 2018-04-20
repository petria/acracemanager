package org.freakz.racemanager.racemanager.service.processrunner;

import lombok.extern.slf4j.Slf4j;
import org.freakz.racemanager.racemanager.Broadcaster;
import org.freakz.racemanager.racemanager.model.HostOS;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;
import org.freakz.racemanager.racemanager.util.HostOsDetector;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static org.freakz.racemanager.racemanager.events.PushEvent.getServerAliveEvent;
import static org.freakz.racemanager.racemanager.events.PushEvent.getServerConsoleLogEvent;

@Service
@Slf4j
public class ServerControlServiceImpl implements ServerControlService {

    class ProcessRunnerNode {

        ProcessRunner runner;

        String serverId;

        ProcessRunnerNode(String serverId) {
            this.serverId = serverId;
        }
    }

    private Timer timer = new Timer();
    private final HostOS hostOS;

    private final Map<String, ProcessRunnerNode> processRunnerNodeMap = new HashMap<>();

    private class MyTimer extends  TimerTask {

        @Override
        public void run() {
            handleTimer();
        }
    }

    private void handleTimer() {
        timer.schedule(new MyTimer(), 5000L);
        for (ProcessRunnerNode node : processRunnerNodeMap.values()) {
            if (node.runner == null) {
                Broadcaster.broadcast(getServerAliveEvent("<not alive>", node.serverId));
            } else {
                Broadcaster.broadcast(getServerAliveEvent("alive: " + node.runner.isAlive(), node.serverId));
            }
        }
    }

    @Override
    public void serverLineAddedToStdout(String serverId, String line) {
        Broadcaster.broadcast(getServerConsoleLogEvent(line, serverId));
    }

    public ServerControlServiceImpl() {
        timer.schedule(new MyTimer(), 5000L);
        HostOsDetector hostOsDetector = new HostOsDetector();
        hostOS = hostOsDetector.detectHostOs();
    }

    private ServerStartupPaths getStartUpPathsWindows() {
        ServerStartupPaths model = new ServerStartupPaths();
        model.setServerDirectory("C:\\AC\\server");
        model.setServerCommand("C:\\AC\\server\\acServer.exe");

        model.setStrackerDirectory("C:\\AC\\stracker");
        model.setStrackerCommand("start-stracker.cmd");

        return model;
    }

    private ServerStartupPaths getStartUpPathsLinux() {
        ServerStartupPaths model = new ServerStartupPaths();
        model.setServerDirectory("/home/petria/AC3/server");
        model.setServerCommand("/home/petria/AC3/server/acServer");

        model.setStrackerDirectory("/home/petria/AC3/stracker");
        model.setStrackerCommand("start-stracker.sh");

        return model;
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
    public void startServer(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);

        log.debug("Started: {}", serverId);
        try {
            node.runner = new ProcessRunnerImpl(serverId, this);
            if (hostOS == HostOS.LINUX) {
                log.debug("Starting Linux server");
                node.runner.startServer(getStartUpPathsLinux());
            } else if (hostOS == HostOS.WINDOWS) {
                log.debug("Starting Windows server");
                node.runner.startServer(getStartUpPathsWindows());
            } else {
                log.error("OS not supported: {}", hostOS);
            }

        } catch (Exception e) {
            log.error("Starting server failed, OS: {}", hostOS, e);
        }
    }

    @Override
    public void stopServer(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);
        if (node.runner != null) {
            node.runner.stopProcess();
            node.runner = null;
        }
    }

    @Override
    public String serverStatus(String serverId) {
        ProcessRunnerNode node = getProcessRunnerNode(serverId);
        if (node.runner == null) {
            return "<not running>";
        }
        return "alive: " + node.runner.isAlive();
    }

    @Override
    public void startStracker(String id) {

    }

    @Override
    public void stopStracker(String id) {

    }
}
