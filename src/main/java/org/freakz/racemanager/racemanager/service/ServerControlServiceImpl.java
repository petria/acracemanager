package org.freakz.racemanager.racemanager.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.racemanager.racemanager.Broadcaster;
import org.freakz.racemanager.racemanager.model.ServerStartupPaths;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

import static org.freakz.racemanager.racemanager.events.PushEvent.getServerConsoleLogEvent;

@Service
@Slf4j
public class ServerControlServiceImpl implements ServerControlService {

    private Timer timer = new Timer();
    private ProcessRunner runner;

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
    public void lineAddedToStdout(String line) {
        Broadcaster.broadcast(getServerConsoleLogEvent(line, "id"));
    }

    public ServerControlServiceImpl() {
        timer.schedule(new MyTimer(), 5000L);
    }

    private ServerStartupPaths getStartUpPaths() {
        ServerStartupPaths model = new ServerStartupPaths();
        model.setServerDirectory("C:\\cygwin64\\home\\airiope\\own\\code\\server");
        model.setServerCommand("C:\\cygwin64\\home\\airiope\\own\\code\\server\\acServer.exe");
        return model;
    }

    private ServerStartupPaths getStartUpPathsLinux() {
        ServerStartupPaths model = new ServerStartupPaths();
        model.setServerDirectory("/home/petria/AC3/server");
        model.setServerCommand("/home/petria/AC3/server/acServer");
        return model;
    }

    @Override
    public void startServer(String id) {
        log.debug("Started: {}", id);
        try {
            runner = new ProcessRunnerImpl(this);
            runner.startServer(getStartUpPathsLinux());

        } catch (Exception e) {
            e.printStackTrace();
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
}
