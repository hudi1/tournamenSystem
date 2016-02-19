package org.tahom.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.TournamentException;

public abstract class TournamentAjaxCheckBox extends AjaxCheckBox {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	public TournamentAjaxCheckBox(String id, IModel<Boolean> model) {
		super(id, model);
	}

	public TournamentAjaxCheckBox(String id) {
		super(id);
	}

	@Override
	protected void onUpdate(AjaxRequestTarget target) {
		try {
			update(target);
		} catch (TournamentException ex) {
			logger.error("Error when submiting button " + this, ex);
			throw ex;
		}
	}

	protected abstract void update(AjaxRequestTarget target);

}
