package org.freakz.racemanager.racemanager.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.freakz.racemanager.racemanager.UIStateManager;
import org.freakz.racemanager.racemanager.ui.view.DefaultView;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@Push
@SpringUI
@SpringViewDisplay
public class RaceManagerUI extends UI implements ViewDisplay, Broadcaster.BroadcastListener {

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private UIStateManager uiStateManager;

    private Panel springViewDisplay;

    private VerticalLayout root;

    private Panel panel;

    private Label test;

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

/*        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("airiot.fi",
                StartServerView.VIEW_NAME));

        root.addComponent(navigationBar);*/
        test = new Label("Server control panel");
        root.addComponent(test);
        Button startButton = new Button("Start server");
        startButton.addClickListener(this::handleStart);
        root.addComponent(startButton);

        Button stopButton = new Button("Stop server");
        stopButton.addClickListener(this::handleStop);
        root.addComponent(stopButton);

        panel = new Panel();
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.addComponent(new Label("Hello!"));
        panel.setContent(panelContent);

        root.addComponent(panel);
        root.setExpandRatio(panel, 1.0f);


/*        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);*/

        Broadcaster.register(this);

    }

    private void handleStop(Button.ClickEvent clickEvent) {
        uiStateManager.stopServer();
    }

    private void handleStart(Button.ClickEvent clickEvent) {
        uiStateManager.startServer();
    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        panel.setContent((Component) view);
    }

    @Override
    public void receiveBroadcast(final String message) {
        access(() -> {
/*            StartServerView view = (StartServerView) viewProvider.getView(StartServerView.VIEW_NAME);
            view.addLine(message);*/
/*            UIScopedView uiScopedView = (UIScopedView)
            uiScopedView.setDateLabel(message);*/
   //         root.addComponent(new Label(message));
            final DefaultView view = (DefaultView) viewProvider.getView("");
            view.setTextArea(message);
            test.setValue(message);

        });
    }

}