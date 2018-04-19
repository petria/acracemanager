/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.freakz.racemanager.racemanager;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import lombok.extern.slf4j.Slf4j;
import org.freakz.racemanager.racemanager.events.PushEvent;
import org.freakz.racemanager.racemanager.views.StartServerView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.security.util.SecurityExceptionUtils;
import org.vaadin.spring.security.util.SuccessfulLoginEvent;

/**
 */
@SpringUI
@Theme(ValoTheme.THEME_NAME)
@Push
@Slf4j
public class SingleSecuredUI extends UI implements Broadcaster.BroadcastListener {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    VaadinSecurity vaadinSecurity;

    @Autowired
    EventBus.SessionEventBus eventBus;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Assetto Corsa Race Manager");
        // Let's register a custom error handler to make the 'access denied' messages a bit friendlier.
        setErrorHandler(new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                if (SecurityExceptionUtils.isAccessDeniedException(event.getThrowable())) {
                    Notification.show("Sorry, you don't have access to do that.");
                } else {
                    super.error(event);
                }
            }
        });
        if (vaadinSecurity.isAuthenticated()) {
            showMainScreen();
        } else {
            showLoginScreen(request.getParameter("goodbye") != null);
        }
    }

    @Override
    public void attach() {
        super.attach();
        Broadcaster.register(this);
        eventBus.subscribe(this);
    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        eventBus.unsubscribe(this);
        super.detach();
    }

    private void showLoginScreen(boolean loggedOut) {
        LoginScreen loginScreen = applicationContext.getBean(LoginScreen.class);
        loginScreen.setLoggedOut(loggedOut);
        setContent(loginScreen);
    }

    private void showMainScreen() {
        setContent(applicationContext.getBean(MainScreen.class));
    }

    @EventBusListenerMethod
    void onLogin(SuccessfulLoginEvent loginEvent) {
        if (loginEvent.getSource().equals(this)) {
            access(new Runnable() {
                @Override
                public void run() {
                    showMainScreen();
                }
            });
        } else {
            // We cannot inject the Main Screen if the event was fired from another UI, since that UI's scope would be active
            // and the main screen for that UI would be injected. Instead, we just reload the page and let the init(...) method
            // do the work for us.
            getPage().reload();
        }
    }

    public void receiveBroadcast(final PushEvent event) {
        //   SecurityContextHolder.getContext().getAuthentication()
        access(() -> {
            switch (event.getType()) {
                case SERVER_CONSOLE_LOG:
                    handleServerLogEvent(event);
                    break;
                default:
                    log.error("Not implemented: {}", event.getType());
            }
        });
    }

    private void handleServerLogEvent(PushEvent event) {
        final Navigator navigator = getNavigator();
        if (navigator != null) {
            final View currentView = getNavigator().getCurrentView();
            if (currentView instanceof StartServerView) {
                StartServerView startServerView = (StartServerView) currentView;
                startServerView.addLineToServerConsole(event.getServerId(), event.getMessage());
            }
        }

    }
}
