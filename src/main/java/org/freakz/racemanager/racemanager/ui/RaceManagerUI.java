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
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.freakz.racemanager.racemanager.ui.view.StartServerView;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@Push
@SpringUI
@SpringViewDisplay
public class RaceManagerUI extends UI implements ViewDisplay, Broadcaster.BroadcastListener {

    @Autowired
    private SpringViewProvider viewProvider;

    private Panel springViewDisplay;
    private VerticalLayout root;

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("airiot.fi",
                StartServerView.VIEW_NAME));

        root.addComponent(navigationBar);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);

        Broadcaster.register(this);

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
        springViewDisplay.setContent((Component) view);
    }

    @Override
    public void receiveBroadcast(final String message) {
        access(() -> {

            final View currentView = getNavigator().getCurrentView();
            if (currentView instanceof StartServerView) {
                StartServerView startServerView = (StartServerView) currentView;
                startServerView.addLine(message);
            }
        });
    }

}