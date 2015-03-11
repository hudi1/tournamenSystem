package org.toursys.web.model;

import org.apache.wicket.model.AbstractReadOnlyModel;

public class ActiveReplaceModel extends AbstractReadOnlyModel<String> {

    private static final long serialVersionUID = 1L;

    private boolean active;

    public ActiveReplaceModel(boolean active) {
        this.active = active;
    }

    @Override
    public String getObject() {
        return active ? "active" : "";
    }

}
