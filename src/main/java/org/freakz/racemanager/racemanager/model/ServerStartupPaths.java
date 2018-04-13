package org.freakz.racemanager.racemanager.model;

import java.io.Serializable;

public class ServerStartupPaths implements Serializable {

    private String serverDirectory;

    private String serverCommand;

    public String getServerDirectory() {
        return serverDirectory;
    }

    public void setServerDirectory(String serverDirectory) {
        this.serverDirectory = serverDirectory;
    }

    public String getServerCommand() {
        return serverCommand;
    }

    public void setServerCommand(String serverCommand) {
        this.serverCommand = serverCommand;
    }
}
