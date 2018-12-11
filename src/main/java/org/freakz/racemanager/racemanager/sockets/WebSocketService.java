package org.freakz.racemanager.racemanager.sockets;

import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.protocol.ws.WebSocketSettings;
import org.apache.wicket.protocol.ws.api.WebSocketPushBroadcaster;
import org.apache.wicket.protocol.ws.api.message.AbstractClientMessage;
import org.apache.wicket.protocol.ws.api.message.ClosedMessage;
import org.apache.wicket.protocol.ws.api.message.ConnectedMessage;
import org.apache.wicket.protocol.ws.api.registry.IWebSocketConnectionRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WebSocketService implements WebSocketInterface {

    private List<ConnectedMessage> connections = new ArrayList<>();
    private Map<String, ConnectedMessage> connectedMessageMap = new HashMap<>();

    private WebSocketPushBroadcaster broadcaster;

    private String getMapKey(AbstractClientMessage message) {
        String application = message.getApplication().getApplicationKey();
        String sessionId = message.getSessionId();
        String key = message.getKey().toString();
        return String.format("%s-%s-%s", application, sessionId, key);
    }

    @Override
    public void removeClient(ClosedMessage message) {
        String mapKey = getMapKey(message);
        ConnectedMessage remove = connectedMessageMap.remove(mapKey);
        log.debug("Removed: {}", mapKey);
    }

    @Override
    public void addClient(ConnectedMessage message) {
        String mapKey = getMapKey(message);

        ConnectedMessage conMsg = new ConnectedMessage(message.getApplication(), message.getSessionId(), message.getKey());
        connectedMessageMap.put(mapKey, conMsg);
        log.debug("Added: {}", mapKey);

        if (null == broadcaster) {
            WebSocketSettings webSocketSettings = WebSocketSettings.Holder.get(message.getApplication());
            IWebSocketConnectionRegistry webSocketConnectionRegistry = webSocketSettings.getConnectionRegistry();
            broadcaster = new WebSocketPushBroadcaster(webSocketConnectionRegistry);
        }
    }

    @Override
    public void sendMessage(int number) {
        if (connectedMessageMap.size() > 0) {
            if (null != broadcaster) {
                WSMessage message = new WSMessage(number);
//				ConnectedMessage connectedMessage = connections.get(0);
                for (ConnectedMessage connectedMessage : connectedMessageMap.values()) {
                    broadcaster.broadcastAll(connectedMessage.getApplication(), message);
                }
//                broadcaster.broadcastAll(connectedMessageMap.values().listIterator().next().getApplication(), message);
            } else {
                throw new RuntimeException("WebSockets can not send message");
            }
        }
    }

}
