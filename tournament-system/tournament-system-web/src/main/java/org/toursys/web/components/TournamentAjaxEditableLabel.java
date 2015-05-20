package org.toursys.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.TournamentException;

public abstract class TournamentAjaxEditableLabel extends AjaxEditableLabel<String> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;

    public TournamentAjaxEditableLabel(String id, IModel<String> model) {
        super(id, model);
    }

    public TournamentAjaxEditableLabel(String id) {
        super(id);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target) {
        super.onSubmit(target);
        try {
            submit(target);
        } catch (TournamentException ex) {
            logger.error("Error when submiting ajax label " + this, ex);
            error(getString(ex.getCode()));
        }
    }

    protected abstract void submit(AjaxRequestTarget target);

}
