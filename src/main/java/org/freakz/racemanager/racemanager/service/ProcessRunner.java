package org.freakz.racemanager.racemanager.service;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class ProcessRunner {

    private final ServerControlServiceImpl serverControlService;
    private String workDir;
    private String command;
    private boolean doRun;
    private Process process;
    private List<String> stdoutLines = new ArrayList<>();

    public ProcessRunner(ServerControlServiceImpl serverControlService) {
        this.serverControlService = serverControlService;
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {

            try {
                doRun = true;
                handleMyTaskRun();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private Timer timer = new Timer();

    public void startProcess(String workDir, String command) {
        this.workDir = workDir;
        this.command = command;
        timer.schedule(new MyTask(), 10L);
    }

    private void handleMyTaskRun() throws IOException {
        log.debug("Starting %s in %s", command, workDir);
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(new File(workDir));
        process = pb.start();

        broadCastLine(">>> Started: " + command);

        process.getOutputStream();
        BufferedReader br;
        String inputEncoding = "UTF-8";
        br = new BufferedReader(new InputStreamReader(process.getInputStream(), inputEncoding));
        while (doRun) {
            doRun = process.isAlive();
            if (doRun) {
                String line = br.readLine();
                broadCastLine(line);
//                stdoutLines.add(line);
                log.debug("server: {}", line);
            }
        }
        process.destroyForcibly();
        broadCastLine(">>> Stopped: " + command);
        log.debug("{} ended", command);
    }

    private void broadCastLine(String line) {
        serverControlService.lineAddedToStdout(line);
    }

    public void stopProcess() {
        process.destroyForcibly();
        this.doRun = false;
    }

    public void isAlive() {
        boolean alive = process.isAlive();
        log.debug("alive: {}", alive);
    }

}
