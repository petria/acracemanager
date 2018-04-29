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
import org.freakz.racemanager.racemanager.events.AliveStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringView(name = StartServerView.VIEW_NAME)
//@Secured({"ROLE_USER", "ROLE_ADMIN"})
@SideBarItem(sectionId = Sections.VIEWS, caption = "Start Server")
@FontAwesomeIcon(FontAwesome.ARCHIVE)
public class StartServerView extends VerticalLayout implements View {

    Logger log = LoggerFactory.getLogger(StartServerView.class);

    public static final String VIEW_NAME = "StartServer";

    @Autowired
    private UIStateManager uiStateManager;

    private final Map<String, ServerAndStrackerView> serverIdToViewMap = new HashMap<>();

    @PostConstruct
    void init() {
        addComponent(new Label("Servers control panel"));

        TabSheet tabSheet = new TabSheet();
        addComponent(tabSheet);

        String serverId = "airiot.fi";
        VerticalLayout tab1 = createServerTab(serverId);
        uiStateManager.initializeServer(serverId);

        tabSheet.addTab(tab1).setIcon(FontAwesome.SERVER);
    }

    private VerticalLayout createServerTab(String serverId) {
        ServerAndStrackerView view = new ServerAndStrackerView(uiStateManager, serverId);
        view.setCaption(serverId);
        serverIdToViewMap.put(serverId, view);
        return view;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    public void addLineToStrackerConsole(String serverId, String text) {
        ServerAndStrackerView view = serverIdToViewMap.get(serverId);
        if (view != null) {
            view.addLineToStrackerConsole(text);
        } else {
            log.error("No view: {}", serverId);
        }
    }

    public void addLineToServerConsole(String serverId, String text) {
        ServerAndStrackerView view = serverIdToViewMap.get(serverId);
        if (view != null) {
            view.addLineToServerConsole(text);
        } else {
            log.error("No view: {}", serverId);
        }
    }

    public void serverAlive(String serverId, AliveStatus serverAlive, AliveStatus strackerAlive) {
        ServerAndStrackerView view = serverIdToViewMap.get(serverId);
        if (view != null) {
            view.serverAlive(serverAlive, strackerAlive);
        } else {
            log.error("No view: {}", serverId);
        }

    }
}