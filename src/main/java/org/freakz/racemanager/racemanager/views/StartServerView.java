package org.freakz.racemanager.racemanager.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import org.freakz.racemanager.racemanager.Sections;
import org.freakz.racemanager.racemanager.UIStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import javax.annotation.PostConstruct;

@SpringView(name = StartServerView.VIEW_NAME)
@Secured({"ROLE_USER", "ROLE_ADMIN"})
@SideBarItem(sectionId = Sections.VIEWS, caption = "Start Server")
@FontAwesomeIcon(FontAwesome.ARCHIVE)

public class StartServerView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "StartServer";

    @Autowired
    private UIStateManager uiStateManager;

    private TextArea textArea;

    @PostConstruct
    void init() {
        addComponent(new Label("Server control panel"));

        VerticalLayout tab1 = new VerticalLayout();

        HorizontalLayout buttons = new HorizontalLayout();

        Button startButton = new Button("Start server");
        startButton.addClickListener(this::handleStart);

        Button stopButton = new Button("Stop server");
        stopButton.addClickListener(this::handleStop);
        stopButton.setEnabled(false);

        Button clearServerLog = new Button("Clear log");
        clearServerLog.addClickListener(this::handleClearLog);

        buttons.addComponent(startButton);
        buttons.addComponent(stopButton);
        buttons.addComponent(clearServerLog);


        textArea = new TextArea();
        textArea.setWidth("100%");
        textArea.setRows(10);

        tab1.addComponent(buttons);
        tab1.addComponent(textArea);

        TabSheet tabSheet = new TabSheet();
        addComponent(tabSheet);

        tab1.setCaption("airiot.fi #1");
        tabSheet.addTab(tab1).setIcon(FontAwesome.SERVER);


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