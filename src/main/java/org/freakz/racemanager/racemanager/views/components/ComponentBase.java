package org.freakz.racemanager.racemanager.views.components;

import com.vaadin.ui.VerticalLayout;
import org.freakz.racemanager.racemanager.UIStateManager;

public class ComponentBase extends VerticalLayout {

    private final UIStateManager uiStateManager;

    public ComponentBase(UIStateManager uiStateManager) {
        this.uiStateManager = uiStateManager;
    }

    public UIStateManager getUiStateManager() {
        return uiStateManager;
    }
}
