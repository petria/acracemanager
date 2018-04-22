package org.freakz.racemanager.racemanager.service.processrunner;


import org.freakz.racemanager.racemanager.model.ServerStartupPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;


public class ProcessRunnerImpl implements ProcessRunner {

    Logger log = LoggerFactory.getLogger(ProcessRunnerImpl.class);

    public enum ProcessType {
        SERVER,
        STRACKER
    }


    private final String serverId;

    private final ProcessType processType;

    private final ServerControlServiceImpl serverControlService;

    private String workDir;

    private String command;

    private boolean doRun;

    private java.lang.Process process;

    final private Timer timer = new Timer();

    public ProcessRunnerImpl(String serverId, ProcessType processType, ServerControlServiceImpl serverControlService) {
        this.serverId = serverId;
        this.processType = processType;
        this.serverControlService = serverControlService;
    }

    private class Process extends TimerTask {

        @Override
        public void run() {
            try {
                doRun = true;
                handleMyTaskRun();
            } catch (Exception e) {
                log.error("Process serverRunner failed!", e);
            }
        }
    }

    @Override
    public void startProcess(ServerStartupPaths model) {
        if (processType == ProcessType.SERVER) {
            doStartProcess(model.getServerDirectory(), model.getServerCommand());
        } else if (processType == ProcessType.STRACKER) {
            doStartProcess(model.getStrackerDirectory(), model.getStrackerCommand());
        } else {
            log.error("Unknown type?!");
        }
    }

    private void doStartProcess(String workDir, String command) {
        this.workDir = workDir;
        this.command = command;
        timer.schedule(new Process(), 10L);
    }

    private void handleMyTaskRun() throws IOException {
        log.debug("Starting {} in {}", command, workDir);


        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.directory(new File(workDir));

        process = pb.start();
        ProcessHandle processHandle = process.toHandle();
        broadCastLine(">>> Started: " + command + " PID: " + processHandle.pid());

        process.getOutputStream();

        BufferedReader br;
        String inputEncoding = "UTF-8";
        br = new BufferedReader(new InputStreamReader(process.getInputStream(), inputEncoding));
        while (doRun) {
            doRun = process.isAlive();
            if (doRun) {
                String line = br.readLine();
                if (line != null) {
                    broadCastLine(line);
                }
            }
        }
        process.destroyForcibly();
        broadCastLine(">>> Stopped: " + command);

        log.debug("{} ended", command);
    }

    private void broadCastLine(String line) {
        log.debug("{} :: {} >> {}", serverId, processType, line);
        serverControlService.handleProcessOutput(serverId, processType, line);
    }

    @Override
    public void stopProcess() {
        if (process != null) {
            ProcessHandle processHandle = process.toHandle();
            long pid = processHandle.pid();
            Stream<ProcessHandle> children = processHandle.children();
            children.forEach(child -> {
                log.debug("Destroy child, PID: {}", child.pid());
                child.destroyForcibly();
            });
            log.debug("Destroying PID: {} ", pid);
            processHandle.destroyForcibly();
            log.debug("Destroyed: {} -- {}", serverId, processType);
        }
        this.doRun = false;
    }

    @Override
    public boolean isAlive() {
        if (process != null) {
            boolean alive = process.isAlive();
            log.debug("{} -- {} - alive: {}", serverId, processType, alive);
            return alive;
        }
        return false;
    }

}
