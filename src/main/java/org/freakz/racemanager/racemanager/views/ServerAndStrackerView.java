package org.freakz.racemanager.racemanager.views;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.freakz.racemanager.racemanager.UIStateManager;
import org.freakz.racemanager.racemanager.model.ServerConfig;
import org.freakz.racemanager.racemanager.model.ServerConfigValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerAndStrackerView extends VerticalLayout {

    Logger log = LoggerFactory.getLogger(ServerAndStrackerView.class);

    private final UIStateManager uiStateManager;

    private TextArea serverTextArea;

    private TextArea strackerTextArea;

    private final String serverId;

    private Label aLiveLabel;

    private TextField basePath;

    private Binder<ServerConfig> binder;

    public ServerAndStrackerView(UIStateManager uiStateManager, String serverId) {

        this.uiStateManager = uiStateManager;
        this.serverId = serverId;

        TabSheet tabSheet = new TabSheet();
        addComponent(tabSheet);

        VerticalLayout controlsAndLogTab = createControlsTab();
        controlsAndLogTab.setCaption("Server & Stracker");

        VerticalLayout configTab = createConfigTab();
        configTab.setCaption("Config");

        tabSheet.addTab(controlsAndLogTab).setIcon(FontAwesome.CAR);
        tabSheet.addTab(configTab).setIcon(FontAwesome.QUESTION);

        addComponent(tabSheet);

    }

    private VerticalLayout createConfigTab() {
        VerticalLayout tab = new VerticalLayout();

        FormLayout layoutWithBinder = new FormLayout();
        binder = new Binder<>();

        basePath = new TextField("Server and Stracker base path");
        basePath.setValueChangeMode(ValueChangeMode.EAGER);

        binder.forField(basePath).bind(ServerConfig::getBasePath, ServerConfig::setBasePath);
        binder.readBean(uiStateManager.getServerConfig(serverId));

        Label infoLabel = new Label();

        NativeButton validate = new NativeButton("Validate");
        validate.addClickListener(this::handleFormValidate);
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(validate);

        layoutWithBinder.addComponent(basePath);

        tab.addComponent(infoLabel);
        tab.addComponent(layoutWithBinder);
        tab.addComponent(buttons);

        return tab;
    }

    private void handleFormValidate(Button.ClickEvent clickEvent) {
        ServerConfig serverConfig = new ServerConfig(serverId);
        try {
            binder.writeBean(serverConfig);
            log.debug("Validate basePathValue: {}", serverConfig.getBasePath());
            final ServerConfigValidation validation = uiStateManager.validateServerConfig(serverConfig);
            int foo = 0;
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private VerticalLayout createControlsTab() {
        VerticalLayout tab = new VerticalLayout();

        aLiveLabel = new Label("Alive: ");

        Button startServerButton = new Button("Start server");
        startServerButton.addClickListener(this::handleServerStart);

        Button stopServerButton = new Button("Stop server");
        stopServerButton.addClickListener(this::handleServerStop);

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

        tab.addComponent(aLiveLabel);
        tab.addComponent(serverControlButtons);
        tab.addComponent(serverTextArea);

        tab.addComponent(strackerControlButtons);
        tab.addComponent(strackerTextArea);

        return tab;
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
