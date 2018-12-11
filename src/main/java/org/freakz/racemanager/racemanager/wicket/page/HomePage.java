package org.freakz.racemanager.racemanager.wicket.page;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.ws.api.WebSocketBehavior;
import org.apache.wicket.protocol.ws.api.WebSocketRequestHandler;
import org.apache.wicket.protocol.ws.api.message.*;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.freakz.racemanager.racemanager.service.Service;
import org.freakz.racemanager.racemanager.sockets.WSMessage;
import org.freakz.racemanager.racemanager.sockets.WebSocketInterface;

@AuthorizeInstantiation("USER")
@Slf4j
@WicketHomePage
public class HomePage extends WebPage {

    @SpringBean
    private Service service;

    @SpringBean
    private WebSocketInterface webSocketInterface;

    private Model<Integer> updateModel;
    private Label numberLabel;

    public HomePage() {

        add(new Label("test", service.getText()));

        updateModel = new Model<Integer>(0);

        numberLabel = new Label("number_label", updateModel);
        numberLabel.setOutputMarkupId(true);
        add(numberLabel);


        Form<Void> form = new Form<>("form");
        TextField textField = new TextField("textField", Model.of("fufuffuf"));
//update the model of the text field each time event "change" occurs
        textField.add(new AbstractAjaxTimerBehavior(Duration.seconds(3)) {
            @Override
            protected void onTimer(AjaxRequestTarget target) {
                IModel<String> model = new IModel<String>() {
                    @Override
                    public String getObject() {
                        return service.getText() + System.nanoTime();
                    }
                };
                textField.setModel(model);
                target.add(textField);
            }
        });

        form.add(textField);
        add(form);

        add(new WebSocketBehavior() {

            @Override
            protected void onClose(ClosedMessage message) {
                super.onClose(message);
                log.debug("onClose: {}", message);
                webSocketInterface.removeClient(message);
            }

            @Override
            protected void onAbort(AbortedMessage message) {
                super.onAbort(message);
                log.debug("onAbort: {}", message);
            }

            @Override
            protected void onConnect(ConnectedMessage message) {
                super.onConnect(message);
                webSocketInterface.addClient(message);
            }

            @Override
            protected void onPush(WebSocketRequestHandler handler, IWebSocketPushMessage message) {
                super.onPush(handler, message);

                if (message instanceof WSMessage) {
                    WSMessage msg = (WSMessage) message;
                    updateModel.setObject(msg.getNumber());
                }
                handler.add(numberLabel);
            }

            @Override
            protected void onMessage(WebSocketRequestHandler handler, TextMessage message) {
                String msg = message.getText();
                // do something with msg
            }
        });
    }
}
