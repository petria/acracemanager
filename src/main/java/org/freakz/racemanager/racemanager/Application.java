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

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import org.atmosphere.cpr.SessionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.support.ApplicationContextEventBroker;
import org.vaadin.spring.security.annotation.EnableVaadinManagedSecurity;
import org.vaadin.spring.security.config.AuthenticationManagerConfigurer;

/**
 * Main entry point into the demo application.
 *
 * @author Petter Holmström (petter@vaadin.com)
 */
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
@EnableVaadinManagedSecurity
public class Application {

    @Autowired
    EventBus.ApplicationEventBus applicationEventBus;

    /**
     * Forward {@link org.springframework.context.ApplicationEvent}s to the {@link org.vaadin.spring.events.EventBus.ApplicationEventBus}.
     */
    @Bean
    ApplicationContextEventBroker applicationContextEventBroker() {
        return new ApplicationContextEventBroker(applicationEventBus);
    }

    @Bean
    public SessionSupport atmosphereSessionSupport() {
        return new SessionSupport();
    }

    /**
     * Provide custom system messages to make sure the application is reloaded when the session expires.
     */
    @Bean
    SystemMessagesProvider systemMessagesProvider() {
        return new SystemMessagesProvider() {
            @Override
            public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
                CustomizedSystemMessages systemMessages = new CustomizedSystemMessages();
                systemMessages.setSessionExpiredNotificationEnabled(false);
                return systemMessages;
            }
        };
    }

    /**
     * Configure the authentication manager.
     */
    @Configuration
    static class AuthenticationConfiguration implements AuthenticationManagerConfigurer {

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user").password("user").roles("USER")
                    .and()
                    .withUser("admin").password("admin").roles("ADMIN")
                    .and()
                    .withUser("a").password("a").roles("ADMIN");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
