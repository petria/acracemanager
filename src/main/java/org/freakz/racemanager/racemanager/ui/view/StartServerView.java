package org.freakz.racemanager.racemanager.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import org.freakz.racemanager.racemanager.UIStateManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = StartServerView.VIEW_NAME)
public class StartServerView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "StartServer";

    @Autowired
    private UIStateManager uiStateManager;

    private TextArea textArea;

    @PostConstruct
    void init() {
        addComponent(new Label("Server control panel"));

        HorizontalLayout buttons = new HorizontalLayout();

        Button startButton = new Button("Start server");
        startButton.addClickListener(this::handleStart);

        Button stopButton = new Button("Stop server");
        stopButton.addClickListener(this::handleStop);

        Button clearServerLog = new Button("Clear log");
        clearServerLog.addClickListener(this::handleClearLog);

        buttons.addComponent(startButton);
        buttons.addComponent(stopButton);
        buttons.addComponent(clearServerLog);


        textArea = new TextArea();
        textArea.setWidth("100%");
        textArea.setRows(10);

        addComponent(buttons);
        addComponent(textArea);
    }

    private void handleClearLog(Button.ClickEvent clickEvent) {
        textArea.setValue("");
    }

    private void handleStop(Button.ClickEvent clickEvent) {
        uiStateManager.stopServer();
    }

    private void handleStart(Button.ClickEvent clickEvent) {
        uiStateManager.startServer();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    public void addLine(String text) {
        String allText = textArea.getValue();
        allText += text + "\n";
        textArea.setReadOnly(false);
        textArea.setValue(allText);
        textArea.setReadOnly(true);
        textArea.setCursorPosition(Integer.MAX_VALUE);
    }

}