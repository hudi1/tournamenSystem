package org.tahom.web.components;

import org.apache.wicket.model.IModel;

public abstract class TournamentBackButton extends TournamentButton {

	private static final long serialVersionUID = 1L;

	public TournamentBackButton(String id, IModel<String> model) {
		super(id, model);
		setDefaultFormProcessing(false);
	}

	public TournamentBackButton(String id) {
		this(id, null);
	}

}
