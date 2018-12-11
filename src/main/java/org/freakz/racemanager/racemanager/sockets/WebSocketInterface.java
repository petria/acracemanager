package org.freakz.racemanager.racemanager.sockets;

import org.apache.wicket.protocol.ws.api.message.ClosedMessage;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;

public interface WebSocketInterface {

    void addClient(ConnectedMessage message);

    void sendMessage(int number);

    void removeClient(ClosedMessage message);
}
