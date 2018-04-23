package org.freakz.racemanager.racemanager.model;

import java.io.Serializable;

public class ServerConfig implements Serializable {

    private String serverId;

    private String basePath;

    public ServerConfig(String serverId) {
        this.serverId = serverId;
        this.basePath = "<not_set>";
    }

    public ServerConfig() {
        this.serverId = "<not_set>";
        this.basePath = "<not_set>";
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
