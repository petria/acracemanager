package org.freakz.racemanager.racemanager.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.freakz.racemanager.racemanager.UIStateManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = StartServerView.VIEW_NAME)
public class StartServerView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "StartServer";

    @Autowired
    private UIStateManager uiStateManager;

    private TextArea textArea;
    private Panel panel;
    private VerticalLayout panelContent;

    @PostConstruct
    void init() {
        addComponent(new Label("Server control panel"));
        Button startButton = new Button("Start server");
        startButton.addClickListener(this::handleStart);
        addComponent(startButton);

        Button stopButton = new Button("Stop server");
        stopButton.addClickListener(this::handleStop);
        addComponent(stopButton);

        panel = new Panel();
        panelContent = new VerticalLayout();
        panelContent.addComponent(new Label("Hello!"));
        panel.setContent(panelContent);

        addComponent(panel);
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

    public void addLine(String line) {
        //textArea.setValue(textArea.getValue() + "\n" + line);
        panelContent.addComponent(new Label(line));
    }

}