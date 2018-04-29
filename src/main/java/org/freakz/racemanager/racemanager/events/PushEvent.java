package org.freakz.racemanager.racemanager.events;

import java.io.Serializable;

public class PushEvent implements Serializable {

    public enum Type {
        SERVER_CONSOLE_LOG,
        STRACKER_CONSOLE_LOG,
        PROCESS_STATUS,

    }

    public static PushEvent getServerConsoleLogEvent(String message, String serverId) {
        return new PushEvent(Type.SERVER_CONSOLE_LOG).setMessage(message).setServerId(serverId);
    }

    public static PushEvent getStrackerConsoleLogEvent(String message, String serverId) {
        return new PushEvent(Type.STRACKER_CONSOLE_LOG).setMessage(message).setServerId(serverId);
    }

    public static PushEvent getServerAliveEvent(String serverId) {
        return new PushEvent(Type.PROCESS_STATUS).setServerId(serverId);
    }

    public PushEvent(Type type) {
        this.type = type;
    }

    private Type type;

    private String message;

    private String serverId;

    private AliveStatus serverAlive;

    private AliveStatus strackerAlive;

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public PushEvent setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getServerId() {
        return serverId;
    }

    public PushEvent setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public AliveStatus getServerAlive() {
        return serverAlive;
    }

    public void setServerAlive(AliveStatus serverAlive) {
        this.serverAlive = serverAlive;
    }

    public AliveStatus getStrackerAlive() {
        return strackerAlive;
    }

    public void setStrackerAlive(AliveStatus strackerAlive) {
        this.strackerAlive = strackerAlive;
    }
}
