package org.freakz.racemanager.racemanager.events;

import java.io.Serializable;

public class PushEvent implements Serializable {

    public enum Type {
        SERVER_CONSOLE_LOG,
        SERVER_ALIVE
    }

    public static PushEvent getServerConsoleLogEvent(String message, String serverId) {
        return new PushEvent(Type.SERVER_CONSOLE_LOG).setMessage(message).setServerId(serverId);
    }

    public static PushEvent getServerAliveEvent(String message, String serverId) {
        return new PushEvent(Type.SERVER_ALIVE).setMessage(message).setServerId(serverId);
    }

    public PushEvent(Type type) {
        this.type = type;
    }

    private Type type;

    private String message;

    private String serverId;

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
}
