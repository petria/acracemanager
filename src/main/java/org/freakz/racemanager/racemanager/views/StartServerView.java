package org.freakz.racemanager.racemanager.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
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

    @PostConstruct
    void init() {
        addComponent(new Label("Server control panel"));

/*        VerticalLayout tab1 = new VerticalLayout();

        HorizontalLayout buttons = new HorizontalLayout();

        Button startServerButton = new Button("Start server");
        startServerButton.addClickListener(this::handleStart);

        Button stopServerButton = new Button("Stop server");
        stopServerButton.addClickListener(this::handleStop);
        stopServerButton.setEnabled(false);

        Button clearServerLog = new Button("Clear log");
        clearServerLog.addClickListener(this::handleClearLog);

        buttons.addComponent(startServerButton);
        buttons.addComponent(stopServerButton);
        buttons.addComponent(clearServerLog);


        serverTextArea = new TextArea();
        serverTextArea.setWidth("100%");
        serverTextArea.setRows(10);



        tab1.addComponent(buttons);
        tab1.addComponent(serverTextArea);

*/
        TabSheet tabSheet = new TabSheet();
        addComponent(tabSheet);

        VerticalLayout tab1 = new ServerAndStrackerView(uiStateManager);
        tab1.setCaption("airiot.fi #1");
        tabSheet.addTab(tab1).setIcon(FontAwesome.SERVER);


    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    public void addLine(String text) {
/*        String allText = serverTextArea.getValue();
        allText += text + "\n";
        serverTextArea.setReadOnly(false);
        serverTextArea.setValue(allText);
        serverTextArea.setReadOnly(true);
        serverTextArea.setCursorPosition(Integer.MAX_VALUE);*/
    }

}