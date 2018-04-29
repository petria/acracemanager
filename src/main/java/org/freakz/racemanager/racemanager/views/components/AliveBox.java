package org.freakz.racemanager.racemanager.views.components;


import com.vaadin.ui.Label;
import org.freakz.racemanager.racemanager.UIStateManager;

public class AliveBox extends ComponentBase {


    private final String format;
    private Label serverAliveLabel;

    public AliveBox(UIStateManager uiStateManager, String format) {
        super(uiStateManager);
        this.format = format;
        serverAliveLabel = new Label(String.format(format, ""));
        addComponent(serverAliveLabel);
    }

    public void update(String status) {
        serverAliveLabel.setValue(String.format(format, status));
    }

}
