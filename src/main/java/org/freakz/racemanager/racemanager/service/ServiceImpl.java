package org.freakz.racemanager.racemanager.service;


import org.freakz.racemanager.racemanager.sockets.WebSocketInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    Random rnd = new Random();
    @Autowired
    private WebSocketInterface webSocketInterface;

    @Override
    public String getText() {
        return "Testing.";
    }

    @Scheduled(fixedRate = 5000)
    public void timer() {
/*        Application application = Application.get("WicketApplication");
        WebSocketSettings webSocketSettings = WebSocketSettings.Holder.get(application);
        IWebSocketConnectionRegistry webSocketConnectionRegistry = webSocketSettings.getConnectionRegistry();
        webSocketConnectionRegistry.getConnection()
        IWebSocketConnection connection = webSocketConnectionRegistry.getConnection(application, sessionId, key);*/
        webSocketInterface.sendMessage(rnd.nextInt());
        System.out.printf(">>timer!!\n");
    }

}
