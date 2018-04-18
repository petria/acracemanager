package org.freakz.racemanager.racemanager.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.freakz.racemanager.racemanager.Sections;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.sidebar.annotation.FontAwesomeIcon;
import org.vaadin.spring.sidebar.annotation.SideBarItem;

import javax.annotation.PostConstruct;

@SpringView(name = GeneralConfigView.VIEW_NAME)
@Secured({"ROLE_ADMIN"})
@SideBarItem(sectionId = Sections.VIEWS, caption = "General Config")
@FontAwesomeIcon(FontAwesome.QUESTION)
public class GeneralConfigView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "GeneralConfigView";

    @PostConstruct
    void init() {
        FormLayout form = new FormLayout();
        TextField tf1 = new TextField("Server path Windows");
        tf1.setRequiredIndicatorVisible(true);

        TextField tf2 = new TextField("Server path Linux");
        tf2.setRequiredIndicatorVisible(true);

        TextField tf3 = new TextField("Windows exe");
        tf3.setRequiredIndicatorVisible(true);

        TextField tf4 = new TextField("Linux exe");
        tf4.setRequiredIndicatorVisible(true);

        form.addComponent(tf1);
        form.addComponent(tf3);

        form.addComponent(tf2);
        form.addComponent(tf4);


        addComponent(form);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
