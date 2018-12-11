package org.freakz.racemanager.racemanager.config;

import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.protocol.ws.javax.JavaxWebSocketFilter;
import org.apache.wicket.protocol.ws.javax.WicketServerEndpointConfig;
import org.apache.wicket.spring.SpringWebApplicationFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.DispatcherType;

@Configuration
public class WebSocketConfiguration {

    @Bean
    public FilterRegistrationBean wicketFilter() {
        final FilterRegistrationBean wicketFilter = new
                FilterRegistrationBean();
        wicketFilter.setDispatcherTypes(DispatcherType.REQUEST,
                DispatcherType.ERROR, DispatcherType.FORWARD, DispatcherType.ASYNC);
        wicketFilter.setAsyncSupported(true);
        wicketFilter.setFilter(new JavaxWebSocketFilter());
        wicketFilter.addInitParameter(WicketFilter.APP_FACT_PARAM,
                SpringWebApplicationFactory.class.getName());
        wicketFilter.addInitParameter(WicketFilter.FILTER_MAPPING_PARAM,
                "/*");
        wicketFilter.addUrlPatterns("/*");
        return wicketFilter;
    }

    //    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public WicketServerEndpointConfig wicketServerEndpointConfig() {
        return new WicketServerEndpointConfig();
    }
}
