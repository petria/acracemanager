package org.freakz.racemanager.racemanager.sockets;

import org.apache.wicket.protocol.ws.api.message.IWebSocketPushMessage;

import java.io.Serializable;

public class WSMessage implements IWebSocketPushMessage, Serializable {
    private static final long serialVersionUID = 1L;

    private final int number;

    public WSMessage(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }


}
