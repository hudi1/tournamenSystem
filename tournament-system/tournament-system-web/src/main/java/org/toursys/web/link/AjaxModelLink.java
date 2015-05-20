package org.toursys.web.link;

import org.apache.wicket.model.ResourceModel;

public abstract class AjaxModelLink<T> extends TournamentAjaxLink {

    private static final long serialVersionUID = 1L;

    public AjaxModelLink(String id) {
        super(id);
        setBody(new ResourceModel(id));
    }

}
