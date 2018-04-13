package org.freakz.racemanager.racemanager.service;

import lombok.extern.slf4j.Slf4j;
import org.freakz.racemanager.racemanager.ui.Broadcaster;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Slf4j
public class ServerControlServiceImpl implements ServerControlService {

//    @Autowired
//    private MyUI myUI;
    private Timer timer = new Timer();
    private ProcessRunner runner;

    private class MyTimer extends  TimerTask {

        @Override
        public void run() {
            handleTimer();
        }
    }

    private void handleTimer() {
        log.debug("Timer!");
        Broadcaster.broadcast(new Date().toString());
        timer.schedule(new MyTimer(), 5000L);
        if (runner != null) {
            runner.isAlive();
        }
    }


    @Override
    public void lineAddedToStdout(String line) {
        Broadcaster.broadcast(line);
    }

    public ServerControlServiceImpl() {
        timer.schedule(new MyTimer(), 5000L);
    }


    @Override
    public void startServer(String id) {
        log.debug("Started: {}", id);
        try {
            runner = new ProcessRunner(this);
            runner.startProcess("C:\\cygwin64\\home\\airiope\\own\\code\\server", "C:\\cygwin64\\home\\airiope\\own\\code\\server\\acServer.exe");

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
