package org.freakz.racemanager.racemanager.views;

import com.vaadin.ui.*;
import org.freakz.racemanager.racemanager.UIStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerAndStrackerView extends VerticalLayout {

    Logger log = LoggerFactory.getLogger(ServerAndStrackerView.class);

    private final UIStateManager uiStateManager;

    private TextArea serverTextArea;

    private TextArea strackerTextArea;

    private final String serverId;

    private final Label aLiveLabel;

    public ServerAndStrackerView(UIStateManager uiStateManager, String serverId) {

        this.uiStateManager = uiStateManager;
        this.serverId = serverId;

        aLiveLabel = new Label("Alive: ");

        Button startServerButton = new Button("Start server");
        startServerButton.addClickListener(this::handleServerStart);

        Button stopServerButton = new Button("Stop server");
        stopServerButton.addClickListener(this::handleServerStop);
//        stopServerButton.setEnabled(false);

        Button clearServerLog = new Button("Clear log");
        clearServerLog.addClickListener(this::handleServerClearLog);

        HorizontalLayout serverControlButtons = new HorizontalLayout();
        serverControlButtons.addComponent(startServerButton);
        serverControlButtons.addComponent(stopServerButton);
        serverControlButtons.addComponent(clearServerLog);

        Button startStrackerButton = new Button("Start Stracker");
        startStrackerButton.addClickListener(this::handleStartStracker);

        Button stopStrackerButton = new Button("Stop Stracker");
        stopStrackerButton.addClickListener(this::handleStopStracker);

        Button clearStrackerLog = new Button("Clear log");
        clearStrackerLog.addClickListener(this::clearStrackerLog);

        HorizontalLayout strackerControlButtons = new HorizontalLayout();
        strackerControlButtons.addComponent(startStrackerButton);
        strackerControlButtons.addComponent(stopStrackerButton);
        strackerControlButtons.addComponent(clearStrackerLog);

        serverTextArea = new TextArea();
        serverTextArea.setWidth("100%");
        serverTextArea.setRows(10);

        strackerTextArea = new TextArea();
        strackerTextArea.setWidth("100%");
        strackerTextArea.setRows(10);

        addComponent(aLiveLabel);
        addComponent(serverControlButtons);
        addComponent(serverTextArea);

        addComponent(strackerControlButtons);
        addComponent(strackerTextArea);

    }

    private void clearStrackerLog(Button.ClickEvent clickEvent) {
        strackerTextArea.setValue("");
    }

    private void handleStopStracker(Button.ClickEvent clickEvent) {
        uiStateManager.stopStracker();
    }

    private void handleStartStracker(Button.ClickEvent clickEvent) {
        uiStateManager.startStracker();
    }

    private void handleServerClearLog(Button.ClickEvent clickEvent) {
        serverTextArea.setValue("");
    }

    private void handleServerStop(Button.ClickEvent clickEvent) {
        uiStateManager.stopServer();
    }

    private void handleServerStart(Button.ClickEvent clickEvent) {
        uiStateManager.startServer();
    }

    public void addLineToStrackerConsole(String text) {
        addLineToTextArea(strackerTextArea, text);
    }

    private void addLineToTextArea(TextArea textArea, String text) {
        String allText = textArea.getValue();
        allText += text + "\n";
        textArea.setReadOnly(false);
        textArea.setValue(allText);
        textArea.setReadOnly(true);
        textArea.setCursorPosition(Integer.MAX_VALUE);

    }

    public void addLineToServerConsole(String text) {
        addLineToTextArea(serverTextArea, text);
    }

    public void serverAlive(String alive) {
        aLiveLabel.setValue("Alive: " + alive);
        log.debug("Server alive!");
    }
}
