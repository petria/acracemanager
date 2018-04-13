package org.freakz.racemanager.racemanager.ui;

import org.freakz.racemanager.racemanager.events.PushEvent;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Broadcaster implements Serializable {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public interface BroadcastListener {

        //        void receiveBroadcast(String message);
        void receiveBroadcast(PushEvent pushEvent);
    }

    private static LinkedList<BroadcastListener> listeners = new LinkedList<>();

    public static synchronized void register(BroadcastListener listener) {
        listeners.add(listener);
    }

    public static synchronized void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

    public static synchronized void broadcast(final PushEvent event) {
        for (final BroadcastListener listener : listeners) {
            executorService.execute(() -> listener.receiveBroadcast(event));
        }
    }
}
