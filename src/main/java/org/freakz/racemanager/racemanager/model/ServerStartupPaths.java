package org.freakz.racemanager.racemanager.model;

import java.io.Serializable;
import java.util.List;

public class ServerStartupPaths implements Serializable {

    private String serverDirectory;

    private String serverCommand;

    private List<String> serverCommands;

    private String strackerDirectory;

    private String strackerCommand;

//    private List<String> serverCommands;

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

    public String getStrackerDirectory() {
        return strackerDirectory;
    }

    public void setStrackerDirectory(String strackerDirectory) {
        this.strackerDirectory = strackerDirectory;
    }

    public String getStrackerCommand() {
        return strackerCommand;
    }

    public void setStrackerCommand(String strackerCommand) {
        this.strackerCommand = strackerCommand;
    }
}
