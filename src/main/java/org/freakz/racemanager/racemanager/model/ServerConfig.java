package org.freakz.racemanager.racemanager.model;

import java.io.Serializable;

public class ServerConfig implements Serializable {

    private String serverId;

    private String basePath;

    public ServerConfig(String serverId) {
        this.serverId = serverId;
    }

    public ServerConfig() {
        this.serverId = "";
        this.basePath = "";
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
