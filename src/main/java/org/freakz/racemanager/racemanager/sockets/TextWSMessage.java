package org.freakz.racemanager.racemanager.sockets;

import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;

import java.io.Serializable;

public class TextWSMessage implements IWebSocketPushMessage, Serializable {
    private static final long serialVersionUID = 1L;

    private final String message;

    public TextWSMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
