package org.freakz.racemanager.racemanager.service.processrunner;

import lombok.extern.slf4j.Slf4j;
import org.freakz.racemanager.racemanager.Broadcaster;
import org.freakz.racemanager.racemanager.model.HostOS;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;
import org.freakz.racemanager.racemanager.util.HostOsDetector;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

import static org.freakz.racemanager.racemanager.events.PushEvent.getServerConsoleLogEvent;

@Service
@Slf4j
public class ServerControlServiceImpl implements ServerControlService {

    private Timer timer = new Timer();
    private ProcessRunner runner;

    private final HostOS hostOS;

    private class MyTimer extends  TimerTask {

        @Override
        public void run() {
            handleTimer();
        }
    }

    private void handleTimer() {
        timer.schedule(new MyTimer(), 5000L);
        if (runner != null) {
            runner.isAlive();
        }
    }

    @Override
    public void serverLineAddedToStdout(String line) {
        Broadcaster.broadcast(getServerConsoleLogEvent(line, "id"));
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
        model.setServerCommand("start-stracker.cmd");

        return model;
    }

    private ServerStartupPaths getStartUpPathsLinux() {
        ServerStartupPaths model = new ServerStartupPaths();
        model.setServerDirectory("/home/petria/AC3/server");
        model.setServerCommand("/home/petria/AC3/server/acServer");

        model.setStrackerDirectory("/home/petria/AC3/stracker");
        model.setServerCommand("start-stracker.sh");

        return model;
    }

    @Override
    public void startServer(String id) {
        log.debug("Started: {}", id);
        try {
            runner = new ProcessRunnerImpl(this);
            if (hostOS == HostOS.LINUX) {
                log.debug("Starting Linux server");
                runner.startServer(getStartUpPathsLinux());
            } else if (hostOS == HostOS.WINDOWS) {
                log.debug("Starting Windows server");
                runner.startServer(getStartUpPathsWindows());
            } else {
                log.error("OS not supported: {}", hostOS);
            }

        } catch (Exception e) {
            log.error("Starting server failed, OS: {}", hostOS, e);
        }
    }

    @Override
    public void stopServer(String id) {
        if (runner != null) {
            runner.stopProcess();
            runner = null;
        }
    }

    @Override
    public String serverStatus(String id) {
        return null;
    }

    @Override
    public void startStracker(String id) {

    }

    @Override
    public void stopStracker(String id) {

    }
}
