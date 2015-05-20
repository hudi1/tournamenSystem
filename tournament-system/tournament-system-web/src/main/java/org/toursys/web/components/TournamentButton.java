package org.toursys.web.components;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toursys.processor.TournamentException;

public abstract class TournamentButton extends Button {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;

    public TournamentButton(String id, IModel<String> model) {
        super(id, model);
    }

    public TournamentButton(String id) {
        super(id);
    }

    @Override
    public void onSubmit() {
        try {
            submit();
        } catch (TournamentException ex) {
            logger.error("Error when submiting button " + this, ex);
            error(getString(ex.getCode()));
        }
    }

    public abstract void submit();

}
