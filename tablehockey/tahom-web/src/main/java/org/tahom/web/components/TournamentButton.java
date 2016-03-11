package org.tahom.web.components;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahom.processor.TournamentException;

public abstract class TournamentButton extends Button {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	public TournamentButton(String id, String value) {
		this(id, Model.of(value));
	}

	public TournamentButton(String id, IModel<String> model) {
		super(id, model);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TournamentButton(String id) {
		this(id, (IModel) null);
	}

	@Override
	public void onSubmit() {
		try {
			submit();
		} catch (TournamentException ex) {
			logger.error("Error when submiting button " + this, ex);
			throw ex;
		}
	}

	public abstract void submit();

}
