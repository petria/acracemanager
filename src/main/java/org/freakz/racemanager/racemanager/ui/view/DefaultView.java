package org.freakz.racemanager.racemanager.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    private TextArea textArea;

    @PostConstruct
    void init() {
        Button clearConsole = new Button("Clear log");
        clearConsole.addClickListener(this::handleClearButton);
        addComponent(clearConsole);

        textArea = new TextArea();
        textArea.setWidth("100%");
        textArea.setRows(25);
        addComponent(textArea);

    }

    private void handleClearButton(Button.ClickEvent clickEvent) {
        textArea.setValue("");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    public void setTextArea(String text) {
        final String value = textArea.getValue();
        //Label.markAsDirty();
        String allText = textArea.getValue();
        allText += text + "\n";
        //        textArea.setValue(allText);
        //        textArea.setCursorPosition(allText.length());
        //        textArea.setCursorPosition(textArea.getValue().length());
        textArea.setReadOnly(false);
        textArea.setValue(allText);
        textArea.setReadOnly(true);
        textArea.setCursorPosition(Integer.MAX_VALUE);
        //        textArea.setSelectionRange(textArea.getValue().length()-1, 1);
        int foo = 0;
    }


}