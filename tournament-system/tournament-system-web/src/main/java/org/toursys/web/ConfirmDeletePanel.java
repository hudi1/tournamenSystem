package org.toursys.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class ConfirmDeletePanel extends Panel {
    public ConfirmDeletePanel(String id, String message) {
        super(id);
        add(new Label("message", message));
        add(new Link("confirm") {
            @Override
            public void onClick() {
                onConfirm();
            }
        });
        add(new Link("cancel") {
            @Override
            public void onClick() {
                onCancel();
            }
        });
    }

    protected abstract void onCancel();

    protected abstract void onConfirm();
}