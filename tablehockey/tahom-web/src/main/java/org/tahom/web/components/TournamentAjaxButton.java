package org.tahom.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.TournamentException;

public abstract class TournamentAjaxButton extends AjaxButton {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	public TournamentAjaxButton(String id, Form<?> form) {
		super(id, form);
	}

	public TournamentAjaxButton(String id, IModel<String> model, Form<?> form) {
		super(id, model, form);
	}

	public TournamentAjaxButton(String id, IModel<String> model) {
		super(id, model);
	}

	public TournamentAjaxButton(String id) {
		super(id);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		try {
			submit(target, form);
		} catch (TournamentException ex) {
			logger.error("Error when submiting button " + this, ex);
			throw ex;
		}
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
		try {
			submit(target, form);
		} catch (TournamentException ex) {
			logger.error("Error when submiting button " + this, ex);
			throw ex;
		}
	}

	protected abstract void submit(AjaxRequestTarget target, Form<?> form);

}
