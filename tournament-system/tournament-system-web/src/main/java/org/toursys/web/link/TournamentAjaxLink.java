package org.toursys.web.link;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.TournamentException;

public abstract class TournamentAjaxLink extends AjaxLink<Void> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;

    public TournamentAjaxLink(String id, IModel<Void> model) {
        super(id, model);
    }

    public TournamentAjaxLink(String id) {
        super(id);
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        try {
            click(target);
        } catch (TournamentException ex) {
            logger.error("Error when click link " + this, ex);
            error(getString(ex.getCode()));
        }
    }

    public abstract void click(AjaxRequestTarget target);

}
