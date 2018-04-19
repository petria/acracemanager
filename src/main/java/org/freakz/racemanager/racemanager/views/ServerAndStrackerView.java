package org.freakz.racemanager.racemanager.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import org.freakz.racemanager.racemanager.UIStateManager;

public class ServerAndStrackerView extends VerticalLayout {

    private final UIStateManager uiStateManager;

    private TextArea serverTextArea;

    private TextArea strackerTextArea;

    public ServerAndStrackerView(UIStateManager uiStateManager) {
        this.uiStateManager = uiStateManager;

        Button startServerButton = new Button("Start server");
        startServerButton.addClickListener(this::handleServerStart);

        Button stopServerButton = new Button("Stop server");
        stopServerButton.addClickListener(this::handleServerStop);
        stopServerButton.setEnabled(false);

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

        addComponent(serverControlButtons);
        addComponent(serverTextArea);

        addComponent(strackerControlButtons);
        addComponent(strackerTextArea);

    }

    private void clearStrackerLog(Button.ClickEvent clickEvent) {
        strackerTextArea.setValue("");
    }

    private void handleStopStracker(Button.ClickEvent clickEvent) {
        uiStateManager.startStracker();
    }

    private void handleStartStracker(Button.ClickEvent clickEvent) {
        uiStateManager.stopStracker();
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

}
