package org.freakz.racemanager.racemanager.model;

import java.io.Serializable;

public class ServerConfig implements Serializable {

    public static final String NOT_SET = "<not_set>";

    private String serverId;

    private String basePath;

    public ServerConfig(String serverId) {
        this.serverId = serverId;
        this.basePath = NOT_SET;
    }

    public ServerConfig() {
        this.serverId = NOT_SET;
        this.basePath = NOT_SET;
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
