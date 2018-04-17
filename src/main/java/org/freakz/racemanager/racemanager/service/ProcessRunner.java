package org.freakz.racemanager.racemanager.service;


import org.freakz.racemanager.racemanager.model.ServerStartupPaths;

public interface ProcessRunner {

    void startServer(ServerStartupPaths model);

    void stopProcess();

    boolean isAlive();
}
