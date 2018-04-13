package org.freakz.racemanager.racemanager.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    private TextArea textArea;

    @PostConstruct
    void init() {
        addComponent(new Label("This is the default view"));
        textArea = new TextArea();
//        textArea.setEnabled(false);

        textArea.setRows(40);
        textArea.setValue("fufufufufuff\ndfufufufuf\n");
        addComponent(textArea);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    public void setTextArea(String text) {
        final String value = textArea.getValue();

        VaadinSession.getCurrent().lock();
        //Label.markAsDirty();
        textArea.setValue(text);

        textArea.markAsDirty();
        VaadinSession.getCurrent().unlock();
        final String value1 = textArea.getValue();
        int foo = 0;
    }


}